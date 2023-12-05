package com.example.myapplication.ui.widget.wave

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Shader
import androidx.annotation.FloatRange
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.transform
import kotlin.math.roundToInt


private const val defaultAmlitude = 0.2f
private const val defaultVelocity = 1.0f
private const val waveDuration = 2000
private const val foreDrawAlpha = 0.5f
private const val scaleX = 1f
private const val scaleY = 1f

private val alphaBitmap by lazy {
    Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
}

data class WaveConfig(
    val foreDrawType: DrawType,
    val backDrawType: DrawType,
    @FloatRange(from = 0.0, to = 1.0) val progress: Float,
    @FloatRange(from = 0.0, to = 1.0) val amplitude: Float,
    @FloatRange(from = 0.0, to = 1.0) val velocity: Float
)

val LocalWave = compositionLocalOf<WaveConfig> {
    error("No Local WaveConfig")
}

@Composable
fun WaveLoading(
    modifier: Modifier = Modifier,
    foreDrawType: DrawType = DrawType.DrawImage,
    backDrawType: DrawType = rememberWaveDrawColor(color = Color.LightGray),
    @FloatRange(from = 0.0, to = 1.0) progress: Float = 0f,
    @FloatRange(from = 0.0, to = 1.0) amplitude: Float = defaultAmlitude,
    @FloatRange(from = 0.0, to = 1.0) velocity: Float = defaultVelocity,
    content: @Composable BoxScope.() -> Unit
) {


    Box(
        modifier.fillMaxSize()
    ) {

        var _size by remember { mutableStateOf(IntSize.Zero) }

        var _bitmap by remember {
            mutableStateOf(Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565))
        }
        AndroidView(
            modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
            factory = { context ->
                // Creates custom view
                object : AbstractComposeView(context) {

                    @Composable
                    override fun Content() {
                        Box(
                            Modifier
                                .wrapContentSize()
                                .onSizeChanged {
                                    _size = it
                                }) {

                            content()
                        }
                    }


                    override fun dispatchDraw(canvas: Canvas) {
                        if (width == 0 || height == 0) return
                        val source = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                        val canvas2 = Canvas(source)
                        super.dispatchDraw(canvas2)
                        _bitmap = Bitmap.createBitmap(
                            source,
                            (source.width - _size.width) / 2,
                            (source.height - _size.height) / 2,
                            _size.width,
                            _size.height
                        )
                        source.recycle()
                    }

                }
            }

        )


        CompositionLocalProvider(
            LocalWave provides WaveConfig(foreDrawType, backDrawType, progress, amplitude, velocity)
        ) {
            with(LocalDensity.current) {
                Box(
                    Modifier
                        .width(_size.width.toDp())
                        .height(_size.height.toDp())
                        .align(Alignment.Center)
                        .clipToBounds()
                ) {
                    WaveLoadingInternal(bitmap = _bitmap)
                }
            }
        }

    }

}


@Composable
private fun WaveLoadingInternal(bitmap: Bitmap) {

    val dp = LocalDensity.current.run {
        1.dp.toPx() //一个dp在当前设备表示的像素量（水波的绘制精度设为一个dp单位）
    }

    val transition = rememberInfiniteTransition()

    val (foreDrawType, backDrawType, progress, amplitude, velocity) = LocalWave.current

    val forePaint = remember(foreDrawType, bitmap) {
        Paint().apply {
            alpha = foreDrawAlpha
            shader = BitmapShader(
                when (foreDrawType) {
                    is DrawType.DrawColor -> bitmap.toColor(foreDrawType.color)
                    is DrawType.DrawImage -> bitmap
                    else -> alphaBitmap
                },
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
            )
        }
    }

    val backPaint = remember(backDrawType, bitmap) {
        Paint().apply {
            shader = BitmapShader(
                when (backDrawType) {
                    is DrawType.DrawColor -> bitmap.toColor(backDrawType.color)
                    is DrawType.DrawImage -> bitmap.toGrayscale()
                    else -> alphaBitmap
                },
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
            )
        }
    }

    val waves = remember(Unit) {
        listOf(
            WaveAnim(waveDuration, 0f, 0f, scaleX, scaleY),
            WaveAnim((waveDuration * 0.75f).roundToInt(), 0f, 0f, scaleX, scaleY),
            WaveAnim((waveDuration * 0.5f).roundToInt(), 0f, 0f, scaleX, scaleY)
        )
    }

    val animates = waves.map { transition.animateOf(duration = it.duration) }


    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        drawIntoCanvas { canvas ->

            //draw back image
            canvas.drawRect(0f, 0f, size.width, size.height, backPaint)

            waves.forEachIndexed { index, wave ->

                canvas.withSave {

                    val maxWidth = 2 * scaleX * size.width / velocity.coerceAtLeast(0.1f)
                    val maxHeight = scaleY * size.height
                    val offsetX = maxWidth / 2 * (1 - animates[index].value) - wave.offsetX
                    val offsetY = wave.offsetY

                    canvas.translate(
                        -offsetX,
                        -offsetY
                    )

                    forePaint.shader?.transform {
                        setTranslate(offsetX, 0f)
                    }

                    canvas.drawPath(
                        wave.buildWavePath(
                            dp = dp,
                            width = maxWidth,
                            height = maxHeight,
                            amplitude = size.height * amplitude,
                            progress = progress
                        ), forePaint
                    )
                }

            }
        }
    }

}


@Composable
private fun InfiniteTransition.animateOf(duration: Int) = animateFloat(
    initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
        animation = tween(duration, easing = CubicBezierEasing(0.4f, 0.2f, 0.6f, 0.8f)),
        repeatMode = RepeatMode.Restart
    ), label = ""
)


/**
 * 位图灰度
 */
private fun Bitmap.toGrayscale(): Bitmap {
    val bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val c = android.graphics.Canvas(bmpGrayscale)
    val paint = android.graphics.Paint()
    val cm = ColorMatrix()
    cm.setSaturation(0f)
    val f = ColorMatrixColorFilter(cm)
    paint.colorFilter = f
    c.drawBitmap(this, 0f, 0f, paint)
    return bmpGrayscale
}


/**
 * 位图单色化
 */
private fun Bitmap.toColor(color: androidx.compose.ui.graphics.Color): Bitmap {
    val bmp = Bitmap.createBitmap(
        width, height, Bitmap.Config.ARGB_8888
    )
    val oldPx = IntArray(width * height) //用来存储原图每个像素点的颜色信息
    getPixels(oldPx, 0, width, 0, 0, width, height) //获取原图中的像素信息

    val newPx = oldPx.map {
        color.copy(android.graphics.Color.alpha(it) / 255f).toArgb()
    }.toTypedArray().toIntArray()
    bmp.setPixels(newPx, 0, width, 0, 0, width, height) //将处理后的像素信息赋给新图
    return bmp
}


///**
// * 转为二值图像
// *
// * @param bmp 原图bitmap
// *
// * @return
// */
//private fun Bitmap.convertToBMW(tmp: Int = 100): Bitmap {
//    val pixels = IntArray(width * height) // 通过位图的大小创建像素点数组
//    // 设定二值化的域值，默认值为100
//    //tmp = 180;
//    getPixels(pixels, 0, width, 0, 0, width, height)
//    var alpha = 0xFF shl 24
//    for (i in 0 until height) {
//        for (j in 0 until width) {
//            val grey = pixels[width * i + j]
//            // 分离三原色
//            alpha = grey and -0x1000000 shr 24
//            var red = grey and 0x00FF0000 shr 16
//            var green = grey and 0x0000FF00 shr 8
//            var blue = grey and 0x000000FF
//            if (red > tmp) {
//                red = 255
//            } else {
//                red = 0
//            }
//            if (blue > tmp) {
//                blue = 255
//            } else {
//                blue = 0
//            }
//            if (green > tmp) {
//                green = 255
//            } else {
//                green = 0
//            }
//            pixels[width * i + j] = (alpha shl 24 or (red shl 16) or (green shl 8)
//                    or blue)
//            if (pixels[width * i + j] == -1) {
//                pixels[width * i + j] = -1
//            } else {
//                pixels[width * i + j] = -16777216
//            }
//        }
//    }
//    // 新建图片
//    val newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//    // 设置图片数据
//    newBmp.setPixels(pixels, 0, width, 0, 0, width, height)
//    val bitmap = ThumbnailUtils.extractThumbnail(newBmp, width, height)
//    return bitmap
//}
//
//
////抖动算法来对图像进行二值化处理
//private fun Bitmap.convertGreyImgByFloyd(): Bitmap {
//    val pixels = IntArray(width * height) //通过位图的大小创建像素点数组
//    getPixels(pixels, 0, width, 0, 0, width, height)
//    val gray = IntArray(height * width)
//    for (i in 0 until height) {
//        for (j in 0 until width) {
//            val grey = pixels[width * i + j]
//            val red = grey and 0x00FF0000 shr 16
//            gray[width * i + j] = red
//        }
//    }
//    var e = 0
//    for (i in 0 until height) {
//        for (j in 0 until width) {
//            val g = gray[width * i + j]
//            if (g >= 128) {
//                pixels[width * i + j] = (android.graphics.Color.alpha(pixels[width * i + j]) shl 24) or -0x1
//                e = g - 255
//
//            } else {
//                pixels[width * i + j] = -0x1000000
//                e = g - 0
//            }
//            if (j < width - 1 && i < height - 1) {
//                //右边像素处理
//                gray[width * i + j + 1] += 3 * e / 8
//                //下
//                gray[width * (i + 1) + j] += 3 * e / 8
//                //右下
//                gray[width * (i + 1) + j + 1] += e / 4
//            } else if (j == width - 1 && i < height - 1) {//靠右或靠下边的像素的情况
//                //下方像素处理
//                gray[width * (i + 1) + j] += 3 * e / 8
//            } else if (j < width - 1 && i == height - 1) {
//                //右边像素处理
//                gray[width * i + j + 1] += e / 4
//            }
//        }
//    }
//    val mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//    mBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
//    return mBitmap
//}
//
//
///**
// * 图片进行二值化黑白
// */
//private fun zeroAndOne(bm: Bitmap): Bitmap? {
//    val width = bm.width //原图像宽度
//    val height = bm.height //原图像高度
//    var color: Int //用来存储某个像素点的颜色值
//    var r: Int
//    var g: Int
//    var b: Int
//    var a: Int //红，绿，蓝，透明度
//    //创建空白图像，宽度等于原图宽度，高度等于原图高度，用ARGB_8888渲染，这个不用了解，这样写就行了
//    val bmp = Bitmap.createBitmap(
//        width, height, Bitmap.Config.ARGB_8888
//    )
//    val oldPx = IntArray(width * height) //用来存储原图每个像素点的颜色信息
//    val newPx = IntArray(width * height) //用来处理处理之后的每个像素点的颜色信息
//    /**
//     * 第一个参数oldPix[]:用来接收（存储）bm这个图像中像素点颜色信息的数组
//     * 第二个参数offset:oldPix[]数组中第一个接收颜色信息的下标值
//     * 第三个参数width:在行之间跳过像素的条目数，必须大于等于图像每行的像素数
//     * 第四个参数x:从图像bm中读取的第一个像素的横坐标
//     * 第五个参数y:从图像bm中读取的第一个像素的纵坐标
//     * 第六个参数width:每行需要读取的像素个数
//     * 第七个参数height:需要读取的行总数
//     */
//    bm.getPixels(oldPx, 0, width, 0, 0, width, height) //获取原图中的像素信息
//    for (i in 0 until width * height) { //循环处理图像中每个像素点的颜色值
//        color = oldPx[i] //取得某个点的像素值
//        r = android.graphics.Color.red(color) //取得此像素点的r(红色)分量
//        g = android.graphics.Color.green(color) //取得此像素点的g(绿色)分量
//        b = android.graphics.Color.blue(color) //取得此像素点的b(蓝色分量)
//        a = android.graphics.Color.alpha(color) //取得此像素点的a通道值
//
//        //此公式将r,g,b运算获得灰度值，经验公式不需要理解
//        var gray = (r.toFloat() * 0.3 + g.toFloat() * 0.59 + b.toFloat() * 0.11).toInt()
//        //下面前两个if用来做溢出处理，防止灰度公式得到到灰度超出范围（0-255）
//        if (gray > 255) {
//            gray = 255
//        }
//        if (gray < 0) {
//            gray = 0
//        }
//        if (gray != 0) { //如果某像素的灰度值不是0(黑色)就将其置为255（白色）
//            gray = 255
//        }
//        newPx[i] = android.graphics.Color.argb(a, gray, gray, gray) //将处理后的透明度（没变），r,g,b分量重新合成颜色值并将其存储在数组中
//    }
//    /**
//     * 第一个参数newPix[]:需要赋给新图像的颜色数组//The colors to write the bitmap
//     * 第二个参数offset:newPix[]数组中第一个需要设置给图像颜色的下标值//The index of the first color to read from pixels[]
//     * 第三个参数width:在行之间跳过像素的条目数//The number of colors in pixels[] to skip between rows.
//     * Normally this value will be the same as the width of the bitmap,but it can be larger(or negative).
//     * 第四个参数x:从图像bm中读取的第一个像素的横坐标//The x coordinate of the first pixels to write to in the bitmap.
//     * 第五个参数y:从图像bm中读取的第一个像素的纵坐标//The y coordinate of the first pixels to write to in the bitmap.
//     * 第六个参数width:每行需要读取的像素个数The number of colors to copy from pixels[] per row.
//     * 第七个参数height:需要读取的行总数//The number of rows to write to the bitmap.
//     */
//    bmp.setPixels(newPx, 0, width, 0, 0, width, height) //将处理后的像素信息赋给新图
//    return bmp //返回处理后的图像
//}
//
//
//private fun gray2Binary(graymap: Bitmap): Bitmap? {
//    //得到图形的宽度和长度
//    val width = graymap.width
//    val height = graymap.height
//    //创建二值化图像
//    var binarymap: Bitmap? = null
//    binarymap = graymap.copy(Bitmap.Config.ARGB_8888, true)
//    //依次循环，对图像的像素进行处理
//    for (i in 0 until width) {
//        for (j in 0 until height) {
//            //得到当前像素的值
//            val col = binarymap.getPixel(i, j)
//            //得到alpha通道的值
//            val alpha = col and -0x1000000
//            //得到图像的像素RGB的值
//            val red = col and 0x00FF0000 shr 16
//            val green = col and 0x0000FF00 shr 8
//            val blue = col and 0x000000FF
//            // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
//            var gray =
//                (red.toFloat() * 0.3 + green.toFloat() * 0.59 + blue.toFloat() * 0.11).toInt()
//            //对图像进行二值化处理
//            gray = if (gray <= 95) {
//                0
//            } else {
//                255
//            }
//            // 新的ARGB
//            val newColor = alpha or (gray shl 16) or (gray shl 8) or gray
//            //设置新图像的当前像素值
//            binarymap.setPixel(i, j, newColor)
//        }
//    }
//    return binarymap
//}

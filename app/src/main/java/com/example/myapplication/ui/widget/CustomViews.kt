package com.example.myapplication.ui.widget

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import kotlin.math.hypot

/**
 * Created by Ethan Cui on 2022/11/8
 */

@Composable
fun backTopbar(
    title: String,
    titleClick: () -> Unit = {},
    backClick: () -> Unit,
    @DrawableRes imgResource: Int = R.mipmap.ic_back_black,
) {
    TopAppBar(
//        backgroundColor = MaterialTheme.colors.,
        elevation = 5.dp,
        title = {
            TextButton(onClick = titleClick) {
                Text(text = title)
            }
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp), onClick = backClick
            ) {
                Icon(
                    painter = painterResource(id = imgResource), contentDescription = ""
                )
            }
        }
    )
}

@Composable
fun NoDescriptionImage(
    @DrawableRes imageResrouce: Int, contentDescription: String = "",
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null
) {
    Image(
        painter = painterResource(id = imageResrouce),
        contentDescription = contentDescription,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        modifier = modifier,
        colorFilter = colorFilter
    )
}

@Composable
inline fun ClickRow(
    noinline onclick: () -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) {
    Row(
//        modifier = modifier.pointerInput(Unit) {
//            detectTapGestures(
//                onTap = onclick
//            )
//        },
        modifier = Modifier
            .clickable(onClick = onclick)
            .then(modifier),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content
    )
}


@Composable
inline fun ClickColumn(
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    rippleColor: Int = R.color.black,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
//        modifier = modifier.pointerInput(Unit) {
//            detectTapGestures(
//                onTap = onclick
//            )
//        },
        modifier = Modifier
            .clickable(onClick = onClick,
                //??????ripple???????????????materialTheme???????????????clickable???onclick??????ripple??????
                indication = rememberRipple(color = Color.White),
                interactionSource = remember {
                    MutableInteractionSource()
                })
            .then(modifier),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

//?????????????????????
@Composable
fun GradientButton(
    gradient: Brush,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(11.dp),
    onClick: () -> Unit = { },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    content: @Composable BoxScope.() -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
        shape = shape,
        elevation = elevation
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

//???????????????????????????????????????????????????brush??????
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GradientButton2(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    gradient: Brush,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor by colors.contentColor(enabled)
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp,
        interactionSource = interactionSource,

        ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .background(brush = gradient)//?????????????????????
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

fun View.createCircularReveal(
//   crossinline doOnEndCallback: () -> Unit={}
): Animator {
    val animator =
        ViewAnimationUtils.createCircularReveal(
            this,
            this.width / 2, this.height / 2,
            1f, hypot(this.width.toDouble(), this.height.toDouble()).toFloat()
        )
    animator.duration = 1600
    animator.interpolator = AccelerateDecelerateInterpolator()
//    animator.addListener(onEnd = {
//        doOnEndCallback()
//    })
    animator.start()
    this.visibility = View.VISIBLE
    return animator
}

class ArcShape(private val progress: Int, val listener: (progress: Int) -> Unit = {}) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, size.height / 2f)
            arcTo(Rect(0f, 0f, size.width, size.height), -90f, progress / 100f * 360f, false)
            close()
        }
        listener(progress)
        return Outline.Generic(path)
    }
}


@Composable
fun CustomSeekbar(
    modifier: Modifier,
    onProgressChanged: (progress: Float) -> Unit
) {
    // ?????????????????????0-1????????? ?????????0
    var progress by remember { mutableStateOf(0f) }
    // bar???????????????
    var barPressed by remember { mutableStateOf(false) }
    // ???????????????, ??????barPressed?????????'??????'????????????????????????
    val radius by animateFloatAsState(if (barPressed) 30f else 20f)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ???????????????
//        Text(text = (progress * 100).toInt().toString(), Modifier.width(30.dp))
        Canvas(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp)
                .pointerInput(Unit) {
                    detectDragGestures( // ??????????????????
                        onDragStart = { barPressed = true },
                        onDragCancel = { barPressed = false },
                        onDragEnd = {
                            // ?????????????????? ??????????????????????????????onProgressChanged??????
                            barPressed = false
                            onProgressChanged.invoke(progress)
                        },
                        onDrag = { change, dragAmount ->
                            // ?????????????????? ????????????progress??????(???????????????????????????)???
                            // ????????????????????? ??????Seekbar????????????(??????)
                            progress = if (change.position.x < 0) {
                                0f
                            } else if (change.position.x > size.width) {
                                1f
                            } else {
                                (change.position.x / this.size.width)
                            }
                        })
                }
                .pointerInput(Unit) {
                    // ????????????????????? ????????????????????????
                    detectTapGestures(onTap = {
                        progress = (it.x / size.width)
                        barPressed = false
                    })
                },
            onDraw = {
                // ??????????????????
                drawLine(
                    color = Color.Gray.copy(alpha = 0.5f),
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = 8f
                )
                // ??????????????????
                drawLine(
                    color = Color.Blue,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width * progress, size.height / 2),
                    strokeWidth = 12f
                )
                // ??????
                drawCircle(
                    color = Color.Blue,
                    radius = radius,
                    center = Offset(size.width * progress, size.height / 2)
                )
            })
    }
}


@Preview
@Composable
fun CircularRevealLayoutPreview() {
    MaterialTheme {
        Scaffold(topBar = {
            TopAppBar() {

            }
        }, content = { padding ->
            padding

        })
    }
}

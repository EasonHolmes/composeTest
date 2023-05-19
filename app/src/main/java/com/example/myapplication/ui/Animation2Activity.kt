package com.example.myapplication.ui

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Created by Ethan Cui on 2023/5/12
 */
class Animation2Activity : BaseActivity() {

    @Composable
    override fun ContentView() {
        Column {
            ScrollText()
//            ScrollText2()
//            DrawableView()
        }
    }

    @Composable
    private fun ScrollText() {
        var target by remember {
            mutableStateOf(resources.displayMetrics.widthPixels + 120)
        }
        val anima by animateSizeAsState(
            targetValue = Size(target.toFloat(), 0f),
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
        )
        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    target = -it.size.width
                }
                .offset(x = LocalDensity.current.run { (anima.width).toDp() }),
        ) {
            Text(
                text = "asdfa", modifier = Modifier,
//                    .offset(x = LocalDensity.current.run { (anima.width).toDp()}),
                overflow = TextOverflow.Clip,
                softWrap = false,
                onTextLayout = {
//                    target =  -it.size.width
                }
            )
        }
    }

    @Composable
    private fun ScrollText2() {
        var target by remember {
            mutableStateOf(0)
        }
        val totalPx = (resources.displayMetrics.widthPixels) + (-target)
        val anim = remember {
            Animatable(totalPx, Int.VectorConverter)
        }
        Text(
            text = "editContent",
            modifier = Modifier
                .padding(top = 50.dp)
                .onPlaced {
                    target = -it.size.width
                }
                .offset {
                    IntOffset(anim.value, 0)
                },
            overflow = TextOverflow.Clip,
            softWrap = false,
            fontSize = 20.sp,
        )
        LaunchedEffect(key1 = target, block = {
            anim.snapTo(totalPx)
            anim.animateTo(
                target,
                animationSpec = infiniteRepeatable(animation = tween(5000, easing = LinearEasing))
            )
        })
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun DrawableView() {
        val path by remember { mutableStateOf(Path()) }
        val stroke = remember {
            Stroke(width = 5f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        }
        var counter by remember { mutableStateOf(0f) }
        var currentCounter by remember {
            mutableStateOf(0f)
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        path.moveTo(event.x, event.y)
                    }

                    MotionEvent.ACTION_MOVE -> {
                        path.lineTo(event.x, event.y)
                        counter = event.x
                    }
                }
                true
            }
            .background(Color.Gray)
        ) {
            Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
                if (currentCounter != counter) {
                    drawPath(path = path, color = Color.Black, style = stroke)
                }
            })
        }

    }

}
package com.example.myapplication.ui.widget

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseInQuart
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun CustomLoading(
    modifier: Modifier = Modifier,
    loadingTxt: String = "Loading...",
    loadingTxtStyle: TextStyle = TextStyle(fontSize = 12.sp),
    size: DpSize = DpSize(70.dp, 70.dp),
    strokeWidth: Float = 20f,
    backgroundColor: Color = Color.LightGray,
    initColor: Color = Color.Red,
    targetColor: Color = Color.Blue,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    val colors by infiniteTransition.animateColor(
        initialValue = initColor,
        targetValue = targetColor,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(modifier) {
        Text(
            text = loadingTxt,
            style = loadingTxtStyle,
            modifier = Modifier
                .align(Alignment.Center)
        )
        Canvas(modifier = Modifier
            .align(Alignment.Center)
            .size(size), onDraw = {
            drawCircle(color = backgroundColor, style = Stroke(width = strokeWidth))
        })

        Canvas(modifier = Modifier
            .align(Alignment.Center)
            .size(size), onDraw = {
            drawArc(
                color = colors,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round,
                    join =
                    StrokeJoin.Round,
                ),
                startAngle = angle - 45f,
                sweepAngle = 45f,
                useCenter = false
            )
        })
        Canvas(modifier = Modifier
            .align(Alignment.Center)
            .size(size), onDraw = {
            drawArc(
                color = colors,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round,
                    join =
                    StrokeJoin.Round,
                ),
                startAngle = -angle,
                sweepAngle = 45f,
                useCenter = false
            )
        })
    }
}


@Composable
fun BreathLoading() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            RepeatMode.Reverse,
        )
    )
    val sweep by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            RepeatMode.Reverse,
        )
    )
    val colors by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            RepeatMode.Reverse,
        )
    )

    Box(modifier = Modifier.scale(scale), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(360.dp))
                .background(colors)
                .size(30.dp)
        )
        Canvas(modifier = Modifier.size(32.dp), onDraw = {
            drawArc(
                color = Color.Black,
                startAngle = angle,
                sweepAngle = 360 / sweep,
                useCenter = false,
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round,
                    join =
                    StrokeJoin.Round,
                ),
            )
        })
    }
}

@Composable
fun BreathLoading3(size: Dp) {
    val rotate by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    val scale by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 1f, targetValue =1.5f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier
            .size(size)
            .rotate(rotate)) {
            drawArc(
                color = Color.Black,
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = 5f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
            drawArc(
                color = Color.Black,
                startAngle = 180f,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = 5f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
        Canvas(modifier = Modifier.size(size-30.dp).scale(scale)) {
            drawArc(color = Color.Black, startAngle = 0f, sweepAngle = 360f, useCenter = false,
                style = Stroke(width = 5*scale, cap = StrokeCap.Round, join = StrokeJoin.Round))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DDD() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BreathLoading3(50.dp)
    }
}
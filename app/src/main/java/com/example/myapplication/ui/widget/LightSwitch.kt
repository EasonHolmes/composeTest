package com.example.myapplication.ui.widget

import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Logutils

@Composable
fun LightSwitch(
    width: Dp = 80.dp, height: Dp = 30.dp,
    backgroundLineColor: Color = Color.Gray,
    lineColor: Color = Color.Black,
    switchColor: Color = Color.Black,
    offSwitchColor: Color = Color.Gray,
    padding: Dp = 6.dp,
    switch: (Boolean) -> Unit
) {
    var switchOn by remember {
        mutableStateOf(false)
    }
    val sweep by animateFloatAsState(
        targetValue = if (switchOn) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing), label = ""
    )
    val radius = height - padding
    Box(modifier = Modifier.clickable(indication = null,
        interactionSource = remember {
            MutableInteractionSource()
        },
        onClick = {
            switchOn = !switchOn
            switch.invoke(switchOn)
        }
    )) {
        Canvas(
            modifier = Modifier
                .size(width = width, height = height)
                .align(alignment = Alignment.Center)
        ) {
            drawRoundRect(
                color = backgroundLineColor,
                style = Stroke(width = 6f, cap = StrokeCap.Round),
                cornerRadius = CornerRadius(42f, 42f)
            )
        }
        Canvas(
            modifier = Modifier
                .size(width = width, height = height)
                .align(alignment = Alignment.Center)
        ) {
            val cornerRadius = CornerRadius(42f, 42f)
            val rect = RoundRect(0f, 0f, size.width, size.height, cornerRadius)
            val path = Path()
            path.addRoundRect(rect, direction = Path.Direction.Clockwise)
            val partialPath = Path()
            val pathMeasure = PathMeasure()
            val stop = (size.width + size.height) * (sweep * 2)
            pathMeasure.setPath(path, true)
            pathMeasure.getSegment(0f, stop, partialPath, true)
            drawPath(
                path = partialPath,
                color = lineColor,
                style = Stroke(width = 7f, cap = StrokeCap.Round)
            )
        }
        Row(
            modifier = Modifier
                .size(width, height)
                .align(alignment = Alignment.Center)
                .padding(start = padding + 3.dp, end = padding + 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "ON", color = lineColor, fontSize = 10.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "OFF", color = backgroundLineColor, fontSize = 10.sp)
        }
        Box(modifier = Modifier
            .offset(
                if (width * sweep - radius <= radius) padding else width * sweep - radius - padding,
                0.dp
            )
            .background(
                if (sweep >= .5f) switchColor else offSwitchColor,
                shape = RoundedCornerShape(360.dp)
            )
            .size(radius)
            .align(alignment = Alignment.CenterStart)
            .clickable(indication = rememberRipple(
                bounded = false,
                color = Color.Gray,
                radius = radius
            ),
                interactionSource = remember {
                    MutableInteractionSource()
                },
                onClick = {
                    switchOn = !switchOn
                    switch.invoke(switchOn)
                }
            )
        )

    }


}

@Preview(showSystemUi = true)
@Composable
fun PreviewSwitch() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LightSwitch {
            Logutils.e("switch=$it")
        }
    }
}
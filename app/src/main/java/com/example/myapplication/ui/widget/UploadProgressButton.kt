package com.example.myapplication.ui.widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication.Logutils


@Composable
fun UploadProgressButton(
    width: Dp,
    height: Dp,
    borderStroke: Dp,
    progressColor:Color,
    buttonColor:Color,
    realProgress: Float,
    onclick: () -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    text = when (realProgress) {
        0f -> {
            "点击开始"
        }

        1f -> {
            "完成"
        }

        else -> {
            ""
        }
    }
    val progressAlpha by animateFloatAsState(
        targetValue = if (realProgress == 0f || realProgress == 1f) 0f else 1f,
        tween(500, 250, easing = LinearEasing), label = ""
    )

    Box(modifier = Modifier.size(width, height), contentAlignment = Alignment.Center) {
        Button(
            onClick = onclick, modifier = Modifier
                .alpha(
                    animateFloatAsState(
                        targetValue = if (realProgress == 0f || realProgress == 1f) 1f else 0f,
                        tween(800, easing = LinearEasing), label = ""
                    ).value
                )
                .size(
                    width = animateDpAsState(
                        targetValue = if (realProgress == 0f || realProgress == 1f) width else height - borderStroke,
                        tween(500, easing = LinearEasing), label = "",
                    ).value, height = height
                ),colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)
        ) {
            Text(text = text)
        }
        // 进度
        Box(
            modifier = Modifier
                .size(height)
                .clip(ArcShape((realProgress * 100).toInt(), listener = {
                }))
                .alpha(
                    progressAlpha
                )
                .background(buttonColor)
        )
        // 白色蒙版
        Box(
            modifier = Modifier
                .size(height - borderStroke)
                .clip(RoundedCornerShape(360.dp))
                .alpha(
                    animateFloatAsState(
                        targetValue = if (realProgress == 0f || realProgress == 1f) 0f else 1f,
                        tween(500, easing = LinearEasing), label = ""
                    ).value
                )
                .background(progressColor)
        )

    }
}
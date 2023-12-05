package com.example.myapplication.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CircleProgress(
    diameter: Dp = 200.dp,
    borderColor: Color = MaterialTheme.colors.primary,
    centerColor: Color = MaterialTheme.colors.secondary,
    borderWidth: Dp = 2.dp,
    progress: Float = 1f
) {
    val sizePiex = LocalDensity.current.run { (diameter).toPx() }

    val roundedCornerShape = RoundedCornerShape(360.dp)
    Canvas(modifier = Modifier
        .clip(roundedCornerShape)
        .border(borderWidth, borderColor, roundedCornerShape)
        .size(diameter), onDraw = {
        drawRect(
            centerColor,
            topLeft = Offset(0f, sizePiex - sizePiex * progress),
            size = Size(sizePiex, sizePiex),
        )

    })
}
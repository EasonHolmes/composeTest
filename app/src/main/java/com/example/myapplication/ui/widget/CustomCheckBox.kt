package com.example.myapplication.ui.widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ChangeStatusCheckBox(
    width: Dp = 85.dp,
    height: Dp = 30.dp,
    startColor: Color = Color.Green,
    endColor: Color = Color.Red,
    thumbColor: Color = Color.White
) {
    var offsetX by remember {
        mutableFloatStateOf(0f)
    }
    val boxSideLengthDp = width / 2
    val boxSildeLengthPx = with(LocalDensity.current) {
        boxSideLengthDp.toPx()
    }
    var isDragg by remember {
        mutableStateOf(false)
    }
    val draggableState = rememberDraggableState {
        offsetX = (offsetX + it).coerceIn(0f, boxSildeLengthPx)
        isDragg = true
    }
    val anima by animateFloatAsState(
        targetValue = offsetX, animationSpec = tween(500, 0, easing = EaseInOutBack),
        label = ""
    )
    val bgColor by animateColorAsState(
        targetValue = if (offsetX == 0f) startColor else endColor,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = "",
    )

    Box(
        modifier = Modifier.width(intrinsicSize = IntrinsicSize.Min)
    ) {
        Spacer(
            modifier = Modifier
                .size(width, height)
                .background(thumbColor, RoundedCornerShape(16.dp))
                .align(Alignment.Center)
        )
        Spacer(modifier = Modifier
            .size(width / 2)
            //由于Modifer链式执行，此时offset必需在draggable与background前面。
            .offset {
                IntOffset(if (isDragg) offsetX.roundToInt() else anima.roundToInt(), 0)
            }
            .background(bgColor, RoundedCornerShape(360.dp))
            .draggable(
                orientation = Orientation.Horizontal,
                state = draggableState,
                onDragStopped = {
                    offsetX = if (offsetX <= boxSildeLengthPx / 2) 0f else boxSildeLengthPx
                })
            .align(
                Alignment.CenterStart
            )
            .clickable(onClick = {
                isDragg = false
                offsetX = if (offsetX > 0f) 0f else boxSildeLengthPx

            },
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                })
        )
        Row(
            Modifier
                .align(Alignment.Center)
                .padding(start = 9.dp, end = 9.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.scale(
                    animateFloatAsState(
                        targetValue = (1 - (offsetX / boxSildeLengthPx)).coerceIn(.7f, 1f),
                        label = ""
                    ).value
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Clear,
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier.scale(
                    animateFloatAsState(
                        targetValue = (offsetX / boxSildeLengthPx).coerceIn(.7f, 1f),
                        label = ""
                    ).value
                )
            )
        }
    }
}

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun ChangeImageSwitch(
    width: Dp = 85.dp,
    height: Dp = 30.dp,
    colors: StatusSwitchColors = ChangeStatusSwitchDefault.colors(),
    startImageVector: ImageVector,
    endImageVector: ImageVector,
    beginLeft: Boolean = true,
    leftOrRight: (bool: Boolean) -> Unit
) {
    ChangeImageSwitchImp(
        width,
        beginLeft,
        colors,
        height,
        startImageVector,
        endImageVector,
        leftOrRight
    )
}

@Composable
private fun ChangeImageSwitchImp(
    width: Dp,
    beginLeft: Boolean,
    colors: StatusSwitchColors,
    height: Dp,
    startImageVector: ImageVector,
    endImageVector: ImageVector,
    leftOrRight: (bool: Boolean) -> Unit
) {
    val boxSildeLengthPx = with(LocalDensity.current) {
        (width / 2 + width/12).toPx()
    }
    var offsetX by remember {
        mutableFloatStateOf(if (beginLeft) 0f else boxSildeLengthPx)
    }
    var isDraggable by remember {
        mutableStateOf(false)
    }
    val draggableState = rememberDraggableState {
        offsetX = (offsetX + it).coerceIn(0f, boxSildeLengthPx)
        isDraggable = true
    }
    val anima by animateFloatAsState(
        targetValue = offsetX, animationSpec = tween(500, 0, easing = EaseInOutBack),
        label = ""
    )
    val circleColor by animateColorAsState(
        targetValue = if (offsetX <= boxSildeLengthPx / 2) colors.startColor else colors.endColor,
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
                .clip(RoundedCornerShape(16.dp))
                .background(colors.startThumbColor, RoundedCornerShape(16.dp))
                .align(Alignment.Center)
                .clickable {
                    isDraggable = false
                    offsetX = if (offsetX > 0f) 0f else boxSildeLengthPx
                }
        )
        Spacer(
            modifier = Modifier
                .size(width / 2)
                //由于Modifer链式执行，此时offset必需在draggable与background前面。
                .offset {
                    IntOffset(if (isDraggable) offsetX.roundToInt() else anima.roundToInt(), 0)
                }
                .background(circleColor, RoundedCornerShape(360.dp))
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = draggableState,
                    onDragStopped = {
                        offsetX = if (offsetX <= boxSildeLengthPx / 2) 0f else boxSildeLengthPx
                    })
                .align(
                    Alignment.CenterStart
                )
                .clickable(
                    onClick = {
                        isDraggable = false
                        offsetX = if (offsetX > 0f) 0f else boxSildeLengthPx
                    },
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = Dp.Unspecified)
                ),
        )
        Row(
            Modifier
                .align(Alignment.Center)
                .padding(horizontal = width/8)
        ) {
            Icon(
                imageVector = startImageVector,
                contentDescription = "",
                tint = if (offsetX <= boxSildeLengthPx / 2) colors.startContentCheckColor else colors.startContentUncheckColor,
                modifier = Modifier
                    .scale(
                        animateFloatAsState(
                            targetValue = (1 - (offsetX / boxSildeLengthPx)).coerceIn(.7f, 1f),
                            label = ""
                        ).value
                    )
                    .weight(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = endImageVector,
                tint = if (offsetX >= boxSildeLengthPx / 2) colors.endContentCheckColor else colors.endContentUncheckColor,
                contentDescription = "",
                modifier = Modifier
                    .scale(
                        animateFloatAsState(
                            targetValue = (offsetX / boxSildeLengthPx).coerceIn(.7f, 1f),
                            label = ""
                        ).value
                    )
                    .weight(1f)
            )
        }
    }
    LaunchedEffect(key1 = offsetX, block = {
        if (offsetX == 0f || offsetX == boxSildeLengthPx)
            leftOrRight(offsetX == 0f)
    })
}

//扁平的样式，放的文字
@Composable
fun ChangeFontSwitch(
    width: Dp = 70.dp,
    height: Dp = 24.dp,
    colors: StatusSwitchColors = ChangeStatusSwitchDefault.colors(),
    startContent: String,
    endContent: String,
    beginLeft: Boolean = true,
    leftOrRight: (bool: Boolean) -> Unit
) {
    ChangeFontSwitchImp(
        width = width,
        height = height,
        colors = colors,
        startContent = startContent,
        endContent = endContent,
        beginLeft = beginLeft,
        leftOrRight = leftOrRight,
    )
}

@Composable
private fun ChangeFontSwitchImp(
    width: Dp = 70.dp,
    height: Dp = 24.dp,
    colors: StatusSwitchColors = ChangeStatusSwitchDefault.colors(),
    startContent: String,
    endContent: String,
    startTxtStyle:TextStyle = TextStyle(fontSize = 12.sp),
    endTxtStyle:TextStyle = TextStyle(fontSize = 12.sp),
    beginLeft: Boolean = true,
    leftOrRight: (bool: Boolean) -> Unit
) {
    val moveWidth =
        LocalDensity.current.run { (width/2).toPx() }
    var offsetX by remember {
        mutableFloatStateOf(if (beginLeft) 0f else moveWidth)
    }
    var isDragg by remember {
        mutableStateOf(false)
    }

    val draggableState = rememberDraggableState {
        offsetX = (offsetX + it).coerceIn(0f, moveWidth)
        isDragg = true
    }
    val anima by animateFloatAsState(
        targetValue = offsetX, animationSpec = tween(500, 0, easing = EaseInOutBack),
        label = "",
        finishedListener = {
        }
    )
    val bgColor by animateColorAsState(
        targetValue = if (offsetX <= moveWidth) colors.startColor else colors.endColor,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = "",
    )

    Box(
        modifier = Modifier
            .width(intrinsicSize = IntrinsicSize.Min)
            .height(height)
    ) {
        Spacer(
            modifier = Modifier
                .size(width, height)
                .clip(RoundedCornerShape(16.dp))
                .background(colors.startThumbColor, RoundedCornerShape(16.dp))
                .align(Alignment.Center)
                .clickable {
                    isDragg = false
                    offsetX = if (offsetX > 0f) 0f else moveWidth
                }
        )
        Spacer(
            modifier = Modifier
                .size(width/2,height)
                //由于Modifer链式执行，此时offset必需在draggable与background前面。
                .offset {
                    IntOffset(
                        if (isDragg) offsetX.roundToInt() else anima.roundToInt(),
                        0
                    )
                }
                .background(bgColor, RoundedCornerShape(360.dp))
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = draggableState,
                    onDragStopped = {
                        offsetX =
                            if (offsetX <= moveWidth/2) 0f else moveWidth
                    })
                .align(
                    Alignment.CenterStart
                )
                .clickable(
                    onClick = {
                        isDragg = false
                        offsetX = if (offsetX > 0f) 0f else moveWidth
                    },
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = Dp.Unspecified)
                ),
        )
        Row(
            Modifier
                .width(width)
                .align(Alignment.Center)
        ) {
            Text(
                startContent,
                style = startTxtStyle,
                modifier = Modifier.weight(1f), textAlign = TextAlign.Center,
                color = if (offsetX <= moveWidth) colors.startContentCheckColor else colors.startContentUncheckColor
            )
            Text(
                endContent,
                modifier = Modifier.weight(1f), textAlign = TextAlign.Center,
                style = endTxtStyle,
                color = if (offsetX >= moveWidth) colors.endContentCheckColor else colors.endContentUncheckColor
            )
        }
    }
    LaunchedEffect(key1 = offsetX, block = {
        if (offsetX == 0f || offsetX == moveWidth)
            leftOrRight(offsetX == 0f)
    })
}


@Composable
fun ChangeNormalSwitch(
    width: Dp = 90.dp,
    height: Dp = 40.dp,
    colors: StatusSwitchColors = ChangeStatusSwitchDefault.colors(),
    beginLeft: Boolean = true,
    leftOrRight: (bool: Boolean) -> Unit
) {
    ChangeNormalSwitchImp(
        width = width,
        height = height,
        colors = colors,
        check = beginLeft,
        checked = leftOrRight,
    )
}

@Composable
private fun ChangeNormalSwitchImp(
    width: Dp = 70.dp,
    height: Dp = 24.dp,
    colors: StatusSwitchColors = ChangeStatusSwitchDefault.colors(),
    check: Boolean = false,
    checked: (bool: Boolean) -> Unit
) {
    val alignDP = 3.dp
    val align = LocalDensity.current.run { alignDP.toPx() }
    val moveWidth =
        LocalDensity.current.run { (width).toPx() } - (align * 2) - LocalDensity.current.run { ((height / 2 - 3.dp) * 2).toPx() }
    var offsetX by remember {
        mutableFloatStateOf(if (check) moveWidth else align)
    }
    var isDragg by remember {
        mutableStateOf(false)
    }

    val draggableState = rememberDraggableState {
        offsetX = (offsetX + it).coerceIn(align, moveWidth)
        isDragg = true
    }
    val anima by animateFloatAsState(
        targetValue = offsetX, animationSpec = tween(500, 0, easing = EaseInOutBack),
        label = "",
        finishedListener = {
        }
    )
    val bgColor by animateColorAsState(
        targetValue = if (offsetX <= moveWidth) colors.startColor else colors.endColor,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = "",
    )

    Box(
        modifier = Modifier
            .width(intrinsicSize = IntrinsicSize.Min)
            .height(height)
    ) {
        Spacer(
            modifier = Modifier
                .size(width, height)
                .clip(RoundedCornerShape(30.dp))
                .background(if (offsetX<=moveWidth/2) colors.startThumbColor else colors.endThumbColor, RoundedCornerShape(16.dp))
                .align(Alignment.Center)
                .clickable {
                    isDragg = false
                    offsetX = if (offsetX > align) align else moveWidth
                }
        )
        Spacer(
            modifier = Modifier
                .size(height - alignDP, height - alignDP)
                //由于Modifer链式执行，此时offset必需在draggable与background前面。
                .offset {
                    IntOffset(
                        if (isDragg) offsetX.roundToInt() else anima.roundToInt(),
                        0
                    )
                }
                .background(bgColor, RoundedCornerShape(360.dp))
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = draggableState,
                    onDragStopped = {
                        offsetX =
                            if (offsetX <= moveWidth/2) align else moveWidth
                    })
                .align(
                    Alignment.CenterStart
                )
                .clickable(
                    onClick = {
                        isDragg = false
                        offsetX = if (offsetX > align) align else moveWidth
                    },
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = Dp.Unspecified)
                ),
        )
    }
    LaunchedEffect(key1 = offsetX, block = {
        if (offsetX == align || offsetX == moveWidth)
            checked(offsetX != align)
    })
}


@Immutable
data class StatusSwitchColors(
    val startColor: Color,
    val endColor: Color,
    val startThumbColor: Color,
    val endThumbColor: Color,
    val startContentCheckColor: Color,
    val startContentUncheckColor: Color,
    val endContentCheckColor: Color,
    val endContentUncheckColor: Color,

    )

object ChangeStatusSwitchDefault {
    @Composable
    fun colors(
        startColor: Color = MaterialTheme.colors.primary,
        endColor: Color = MaterialTheme.colors.secondary,
        startThumbColor: Color = Color.White,
        endThumbColor:Color = Color.White,
        startFontCheckColor: Color = Color.White,
        startFontUncheckColor: Color = endColor,
        endFontCheckColor: Color = Color.White,
        endFontUncheckColor: Color = startColor,
    ): StatusSwitchColors = StatusSwitchColors(
        startColor,
        endColor,
        startThumbColor,
        endThumbColor,
        startFontCheckColor,
        startFontUncheckColor,
        endFontCheckColor,
        endFontUncheckColor
    )
}

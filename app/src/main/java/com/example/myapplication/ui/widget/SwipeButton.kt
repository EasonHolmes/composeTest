package com.example.myapplication.ui.widget

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Logutils
import com.example.myapplication.ui.mytheme.LightDarkTheme
import com.skydoves.flexible.core.InternalFlexibleApi
import com.skydoves.flexible.core.toPx
import kotlin.math.roundToInt


val GreenColor = Color(0xFF2FD286)

// 1. 定义锚点 可以是多个
enum class DragAnchors {
    Start,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeButton(complete: (progress: Float, complete: Boolean) -> Unit) {
    val maxWidth = 350.dp
    val controlWidth = 50.dp
    val density = LocalDensity.current

    val sizePx = with(LocalDensity.current) { (maxWidth - controlWidth).toPx() }
    // 2. 使用 remember 声明 AnchoredDraggableState,确保重组过程中能够缓存结果
    val state = remember {
        AnchoredDraggableState(
            // 3. 设置 AnchoredDraggableState 的初始锚点值
            initialValue = DragAnchors.Start,

            // 4. 根据行进的距离确定我们是否对下一个锚点进行动画处理。在这里，我们指定确定我们是否移动到下一个锚点的阈值是到下一个锚点距离的一半——如果我们移动了两个锚点之间的半点，我们将对下一个锚点进行动画处理，否则我们将返回到原点锚点。
            positionalThreshold = { totalDistance ->
                Logutils.e("totaod===" + totalDistance)
                totalDistance / 2
            },

            // 5.确定将触发拖动内容以动画形式移动到下一个锚点的最小速度，已而不管是否 达到 positionalThreshold 指定的阈值。
            velocityThreshold = {
                with(density) {
                    100.dp.toPx()
                }
            },

            // 6. 指定了在释放拖动手势时如何对下一个锚点进行动画处理;这里我们使用 一个补间动画，它默认为 FastOutSlowIn 插值器
            animationSpec = tween(),

            confirmValueChange = { newValue ->
                true
            }
        ).apply {

            // 7. 使用前面介绍的 updateAnchors 方法定义内容的锚点
            updateAnchors(

                // 8. 使用 DraggableAnchors 帮助程序方法指定要使用的锚点 。我们在这里所做的是创建 DragAnchors 到内容的实际偏移位置的映射。在这种情况下，当状态为“开始”时，内容将偏移量为 0 像素，当状态为“结束”时，内容偏移量将为 400 像素。
                DraggableAnchors {
                    DragAnchors.Start at 0f//对应锚点的位置
                    DragAnchors.End at sizePx//对应锚点的位置
                }
            )
        }
    }
    val progress by remember {
        derivedStateOf {
            if (state.offset == 0f) 0f else state.offset / sizePx
        }
    }
    Box(
        modifier = Modifier
            .width(maxWidth)
            .anchoredDraggable(
                state = state,
                orientation = Orientation.Horizontal,
            )
            .background(GreenColor, RoundedCornerShape(controlWidth))
    ) {
        Column(
            Modifier
                .align(Alignment.Center)
                .alpha(1f - progress),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("手势拖动，有锚点动画", color = Color.White, fontSize = 18.sp)
            Text("Swipe to confirm", color = Color.White, fontSize = 12.sp)
        }

        DraggableControl(
            modifier = Modifier
                .offset { IntOffset(state.offset.roundToInt(), 0) }
                .size(controlWidth),
            progress = progress
        )
    }
    LaunchedEffect(key1 = progress) {
        complete.invoke(progress, progress == 1f)

    }
}

@Composable
private fun DraggableControl(
    modifier: Modifier,
    progress: Float
) {
    Box(
        modifier
            .padding(4.dp)
            .shadow(elevation = 2.dp, CircleShape, clip = false)
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = progress >= 0.8, label = "") {
            if (it) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    tint = GreenColor
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = GreenColor
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmationButtonPreview() {
    LightDarkTheme {
        Scaffold { paddingValues: PaddingValues ->
            paddingValues
            Column(modifier = Modifier.padding(paddingValues)) {
                SwipeButton {progress, complete ->  
                }
            }
        }
    }
}
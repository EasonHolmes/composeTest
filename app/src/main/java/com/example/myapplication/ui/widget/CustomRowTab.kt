package com.example.myapplication.ui.widget

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun <T> RowTabUI(
    modifier: Modifier = Modifier,
    items: List<T>,
    rowTabStyle: RowTabStyle = RowTabStyleDefault.style(),
    tabPadding: PaddingValues = PaddingValues(),
    animationSpec: AnimationSpec<Int> = tween(350, easing = EaseInOutBack),
    initSelectIndex: Int = 0,
    onSelectIndex: (Int) -> Unit,//用于点击即时回调
    //用于动画后再做操作，比如背景和选中的文字颜色一样，那在即时回调里，圆形游标还没有过去，文字已经变色就看不到了，等游标过去才看到就很突兀
    animFinish: (Int) -> Unit = {},
    content: @Composable BoxScope.(Int, T) -> Unit
) {
    var tabWidthPx by remember {
        mutableIntStateOf(0)
    }
    var movePx by remember {
        mutableIntStateOf(0)
    }
    var selectIndex by remember {
        mutableIntStateOf(0)
    }
    val anima by animateIntAsState(
        targetValue = movePx,
        animationSpec = animationSpec,
        "", finishedListener = {
            animFinish(selectIndex)
        }
    )
    val localDensity = LocalDensity.current
    Box(
        modifier = Modifier
            .shadow(rowTabStyle.elevation, shape = rowTabStyle.roundedCornerShape)
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
            .background(rowTabStyle.bgColor, rowTabStyle.roundedCornerShape)
            .onSizeChanged {
                tabWidthPx = it.width / items.size
            }
            .then(modifier)

    ) {
        Spacer(
            modifier = Modifier
                .offset {
                    IntOffset(anima, 0)
                }
                .width(localDensity.run { (tabWidthPx).toDp() })
                .background(rowTabStyle.tabBgBrush, rowTabStyle.roundedCornerShape)
                .align(alignment = Alignment.BottomStart)
                .then(
                    if (rowTabStyle.rowTabStyle == TabStyle.LINE) {
                        Modifier.height(rowTabStyle.tabHeight)
                    } else {
                        Modifier.fillMaxHeight()
                    }
                )
        )
        Row {
            items.forEachIndexed { index, item ->
                Box(modifier = Modifier
                    .weight(1f)
                    .clickable {
                        movePx = tabWidthPx * (index)
                        selectIndex = index
                        onSelectIndex(index)
                    }
                    .padding(tabPadding)) {
                    content(index, item)
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit, block = {
        movePx = tabWidthPx * initSelectIndex
        onSelectIndex(initSelectIndex)
    })
}

@Immutable
data class RowTabStyle(
    val selectTextColor: Color,
    val unSelectTextColor: Color,
    val elevation: Dp,
    val bgColor: Color,
    val tabHeight: Dp,
    val tabBgBrush: Brush,
    val roundedCornerShape: RoundedCornerShape,
    val rowTabStyle: TabStyle,
)

@Immutable
enum class TabStyle {
    LINE,
    ROUND,
}

object RowTabStyleDefault {
    fun style(
        selectTextColor: Color = Color.Black,
        unSelectTextColor: Color = Color.Gray,
        elevation: Dp = 0.dp,
        bgColor: Color = if (elevation == 0.dp) Color.Transparent else Color.White,//要elevation必须设置背景色，要透明就不能有elevation
        tabHeight: Dp = 2.dp,
        tabBgBursh: Brush = Brush.horizontalGradient(listOf(Color.Black, Color.Black)),
        roundedCornerShape: RoundedCornerShape = RoundedCornerShape(0),
        rowTabStyle: TabStyle = TabStyle.LINE
    ): RowTabStyle =
        RowTabStyle(
            selectTextColor,
            unSelectTextColor,
            elevation,
            bgColor,
            tabHeight,
            tabBgBursh,
            roundedCornerShape,
            rowTabStyle
        )
}


package com.example.myapplication.ui.widget

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.Logutils


@Composable
fun ScrollableLazyColumn2(
    listState: LazyListState,
    scrollDirectionCallback: (ScrollDirection) -> Unit,
    content: @Composable () -> Unit
) {
    val offset = remember {
        mutableIntStateOf(0)
    }
    var lastDirection by remember {
        mutableStateOf(ScrollDirection.DOWN)
    }
    var lastFirstVisibleItemScrollOffset by remember {
        mutableIntStateOf(0)
    }
    var lastFirstVisibleItemIndex by remember {
        mutableIntStateOf(0)
    }
    val  firstVisibleItemIndex by remember {
        derivedStateOf {listState.firstVisibleItemIndex  }
    }
//    手指向上滑动（即正方向）时offset会变大，向下时变小
    val firstVisibleItemScroolloffset by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset }
    }
    if (listState.isScrollInProgress) {
        run {
            //第一个可见的item改变了
            if (firstVisibleItemIndex != lastFirstVisibleItemIndex) {
                lastFirstVisibleItemIndex = firstVisibleItemIndex
                lastFirstVisibleItemScrollOffset = firstVisibleItemScroolloffset
                return@run
            }
            //第一个可见item当前的offset - 上一次记录的offset
            offset.intValue =
                firstVisibleItemScroolloffset - lastFirstVisibleItemScrollOffset

            if (offset.intValue < -5 && lastDirection!=ScrollDirection.DOWN) {//负数为向下 正数为向上，-5是个大概阻尼值 0就是没有也是分界点
                Logutils.e("向下滑动"+offset.intValue)
                lastDirection = ScrollDirection.DOWN
                scrollDirectionCallback(lastDirection)
            } else if (offset.intValue > 5 && lastDirection!=ScrollDirection.UP) {
                Logutils.e("向上滑动"+offset.intValue)
                lastDirection = ScrollDirection.UP
                scrollDirectionCallback(lastDirection)
            }
            //记录第一个可见item当前的offset 这是个阻尼值滑动越快值越大
            lastFirstVisibleItemScrollOffset = firstVisibleItemScroolloffset
        }
    }
    content.invoke()

}

//根据firstvisibleIndex来判断向上还是向下
@Composable
fun ScrollableLazyColumn(
    listState: LazyListState,
    scrollDirectionCallback: (ScrollDirection) -> Unit,
    content: @Composable () -> Unit
) {
    val firstVisibleItemindex = remember {
        derivedStateOf { listState.firstVisibleItemIndex }//只是计算使用derivedStateOf如果要发送事件使用snapshotFlow
    }
    var lastFistVisibleIndex by remember {
        mutableIntStateOf(0)
    }
    var lastDirection by remember {
        mutableStateOf(ScrollDirection.DOWN)
    }
    if (firstVisibleItemindex.value > lastFistVisibleIndex) {
        if (lastDirection != ScrollDirection.UP) {//避免重复调用回调
            lastDirection = ScrollDirection.UP
            scrollDirectionCallback(lastDirection)
        }
    } else if (firstVisibleItemindex.value < lastFistVisibleIndex) {
        if (lastDirection != ScrollDirection.DOWN) {//避免重复调用回调
            lastDirection = ScrollDirection.DOWN
            scrollDirectionCallback(lastDirection)
        }
    }
    lastFistVisibleIndex = firstVisibleItemindex.value
    content.invoke()
}

enum class ScrollDirection {
    UP, DOWN, NONE
}


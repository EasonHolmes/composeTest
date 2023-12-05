package com.example.myapplication.ui.coordinator

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.Logutils
import com.example.myapplication.ui.BaseActivity
import com.example.myapplication.ui.widget.ScrollDirection
import com.example.myapplication.ui.widget.ScrollableLazyColumn
import com.example.myapplication.ui.widget.ScrollableLazyColumn2

class CoordinatorLayoutActivity : BaseActivity() {
    private val listData by lazy {
        val list = mutableListOf<Int>()
        for (data in 0..100) {
            list.add(data)
        }
        list
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ContentView() {
        Column {
            //topbar跟随 start
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()//一直跟随
            val ddd = scrollBehavior.state.contentOffset
//        Logutils.e("dddd===" + ddd)
//        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()//仅在内容一直向下滚动时才会展开应用栏
            Scaffold(
                topBar = {
                    androidx.compose.material3.TopAppBar(
                        title = { Text(text = "title") },
                        scrollBehavior = scrollBehavior,  //topbar跟随
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray)
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),  //topbar跟随
            ) { paddingValues ->
                paddingValues
                LazyColumn(
                    content = {
                        itemsIndexed(items = listData, itemContent = { index, item ->
                            Text(
                                text = item.toString(),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        })
                    },
                    verticalArrangement = Arrangement.spacedBy(15.dp), state = rememberLazyListState()
                )
            }
            //topbar跟随 end
            Text(
                text = "---------------分割线------------------",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            var isUP by remember {
                mutableStateOf(false)
            }
            //根据lazycolumn向上向下做动画start
            val lazyListState = rememberLazyListState()
            Box(Modifier.weight(1f)) {
                ScrollableLazyColumn2(listState = lazyListState, scrollDirectionCallback = {
                    Logutils.e("iiiiii===" + it.name)
                    isUP = it == ScrollDirection.UP
                }) {
                    LazyColumn(
                        content = {
                            itemsIndexed(items = listData, itemContent = { index, item ->
                                Text(
                                    text = item.toString(),
                                    modifier = Modifier.fillMaxWidth().height(100.dp),
                                    textAlign = TextAlign.Center
                                )
                            })
                        },
                        verticalArrangement = Arrangement.spacedBy(15.dp), state = lazyListState
                    )
                }
                Spacer(
                    modifier = Modifier
                        .offset(
                            0.dp, animateDpAsState(
                                targetValue = if (isUP) (60).dp else (-50).dp,
                                tween(200, easing = LinearEasing), label = ""
                            ).value
                        )
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(alignment = Alignment.BottomCenter)
                        .background(
                            Color.Black,
                            RoundedCornerShape(16.dp)
                        )
                )
            }
            //根据lazycolumn向上向下做动画end


        }

    }

    override fun needTopbar(): Boolean {
        return false
    }
}

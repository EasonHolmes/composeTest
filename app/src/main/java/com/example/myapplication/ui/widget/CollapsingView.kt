package com.example.myapplication.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.vm.ExampleUiData

/**
 * Created by Ethan Cui on 2022/11/24
 */

@Composable
fun <T> CollapsingEffectScreen(
    items: MutableList<T>,
    itemContent: @Composable LazyItemScope.(index: Int, item: T) -> Unit,
    header: @Composable LazyItemScope.() -> Unit,
) {
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    LazyColumn(
        Modifier.fillMaxSize(),
        lazyListState,
    ) {
        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .graphicsLayer {
                    scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                    translationY = scrolledY * 0.5f
                    previousOffset = lazyListState.firstVisibleItemScrollOffset
                }) {
                header()
            }
        }
        itemsIndexed(items) { index, item ->
            itemContent(index, item)
        }
    }
}
@Preview
@Composable
fun CollapsingViewPreview() {
    val items = (1..100).map { "Item $it" }.toMutableList()
    MaterialTheme {
        Scaffold(topBar = {
            TopAppBar() {

            }
        }, content = { padding ->
            padding
            CollapsingEffectScreen(items = items, header = {
                Image(
                    painter = painterResource(id = R.mipmap.toolbar_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            },itemContent = { index, item ->
                Text(
                    text = item,
                    Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            })
        })
    }
}

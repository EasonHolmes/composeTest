package com.example.myapplication.ui.widget

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.pintu.ImagePiece
import com.example.myapplication.ui.pintu.ImageSplitter
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PinTuWidget(imgResources: Int, columns: Int) {
    val context = LocalContext.current
    val mBitmap by remember {
        mutableStateOf(
            BitmapFactory.decodeResource(
                context.resources,
                imgResources
            )
        )
    }
    val mItemBitmaps = remember {
        val list = mutableStateListOf<ItemImage>()
        ImageSplitter.split(mBitmap, columns).forEachIndexed { index, item ->
            list.add(ItemImage(index, item))
        }
        list.sortWith { lhs, rhs -> if (Math.random() > 0.5) 1 else -1 }
        list
    }
    var lastClickIndex by remember {
        mutableIntStateOf(-1)
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        content = {
            itemsIndexed(
                key = { index: Int, item: ItemImage -> item.index.toString() },
                items = mItemBitmaps, itemContent = { index, item ->
                    var imageSize by remember {
                        mutableStateOf(IntSize(0, 0))
                    }
                    Box(modifier = Modifier.animateItemPlacement()) {
                        Image(
                            bitmap = item.imagePiece.bitmap.asImageBitmap(),
                            contentDescription = "",
                            modifier = Modifier
                                .clickable {
                                    if (lastClickIndex != -1) {
                                        swapItems(mItemBitmaps, lastClickIndex, index)
                                        lastClickIndex = -1
                                        mItemBitmaps.forEach { it.select.value = false }
                                        mItemBitmaps.forEachIndexed { index, item ->
                                            if (index + 1 != mItemBitmaps.size && item.index + 1 != mItemBitmaps[index + 1].index) {
                                                return@clickable
                                            }
                                        }
                                        Toast
                                            .makeText(context, "success", Toast.LENGTH_SHORT)
                                            .show()
                                    } else {
                                        lastClickIndex = index
                                        mItemBitmaps[index].select.value = true
                                    }
                                }
                                .onSizeChanged {
                                    imageSize = it
                                }
                        )
                        if (item.select.value)
                            Spacer(
                                modifier = Modifier
                                    .alpha(.2f)
                                    .background(Color.Red)
                                    .size(
                                        LocalDensity.current.run { imageSize.width.toDp() },
                                        LocalDensity.current.run { imageSize.height.toDp() })
                                    .alpha(.5f)
                            )
                    }
                })
        }
    )
//    var list = (0..20).toList().sortedDescending().toMutableStateList()
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        Row {
//            Button(onClick = {
////                val first = list.first()
////                list.add(0, first + 1)
//                swapItems(list,2,5)
//            }) {
//                Text(text = "添加")
//            }
//
//            Button(onClick = {
//                val random = list.random()
//                list.remove(random)
//            }, modifier = Modifier.padding(start = 10.dp)) {
//                Text(text = "移除")
//            }
//        }
//
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(10.dp)
//        ) {
//            items(list.count(), key = { index: Int -> list[index].toString() }) { index ->
//
//                Text(
//                    text = "列表项目${list[index]}",
//                    modifier = Modifier.animateItemPlacement()
//                )
//            }
//        }
//    }


}

private fun <T> swapItems(list: MutableList<T>, index1: Int, index2: Int) {
    val temp = list[index1]
    list[index1] = list[index2]
    list[index2] = temp
}

private data class ItemImage(
    val index: Int,
    val imagePiece: ImagePiece,
    var select: MutableState<Boolean> = mutableStateOf(false)
)


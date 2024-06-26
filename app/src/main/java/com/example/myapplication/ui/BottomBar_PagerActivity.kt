package com.example.myapplication.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import kotlinx.coroutines.launch

/**
 * Created by Ethan Cui on 2023/1/11
 */
class BottomBar_PagerActivity : BaseActivity() {


    @Composable
    override fun ContentView() {
        ScaffoldBottomBarSamples()
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ScaffoldBottomBarSamples() {
        val bottomNumber = listOf("首页", "清除", "电话")
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = {3}
        )
        Scaffold( bottomBar = {
            BottomNav(bottomNumber,pagerState)
        }, content = { paddingValues: PaddingValues ->
            androidx.compose.foundation.pager.HorizontalPager(
                modifier = Modifier.fillMaxHeight(),
                state = pagerState,
                beyondBoundsPageCount = 3
            ) { page ->
                Log.e("ethan",pagerState.currentPage.toString())
                when (pagerState.currentPage) {
                    0 -> {
                        Tab1(Modifier.padding(paddingValues),1)
//                        Text(text = "我是整个页面最重要的内容$page", Modifier.padding(paddingValues))
                    }
                    1 -> {
                        Tab1(Modifier.padding(paddingValues),2)

//                        Text(text = "我是整个页面最重要的内容$page", Modifier.padding(paddingValues))
                    }
                    2 -> {
                        Tab1(Modifier.padding(paddingValues),3)
//                        Text(text = "我是整个页面最重要的内容$page", Modifier.padding(paddingValues))
                    }
                }
            }
        })
    }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun BottomNav(bottomNumber: List<String>,pagerState: androidx.compose.foundation.pager.PagerState) {
        val scope = rememberCoroutineScope()
        val bottomIcon =
            listOf(Icons.Default.Home, Icons.Default.Clear, Icons.Default.Call, Icons.Default.Email)
        BottomNavigation {
            bottomNumber.forEachIndexed { index, contentText ->
                BottomNavigationItem(
                    modifier = Modifier.background(Color.Black),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }, label = {
                        Text(
                            text = contentText,
                            color = if (pagerState.currentPage == index) Color.Blue else Color.White
                        )
                    }, icon = {
//                            if (checkIndex == index) {
//                                BadgeBox(badgeContent = {
                                    //显示消息气泡99+
//                                    Text(text = "99+")
//                                }, contentColor = Color.White, backgroundColor = Color.Red) {
//                                    Icon(imageVector = bottomIcon[index], contentDescription = null)
//                                }
//                            } else {
                        Icon(imageVector = bottomIcon[index], contentDescription = null)
//                            }
                    }, selectedContentColor = Color.Red, unselectedContentColor = Color.White
                )
            }
        }
    }
    @Composable
    fun Tab1(modifier: Modifier,number:Int){
        var count by remember {
            mutableStateOf(0)
        }
        count += 1
        Log.e("ethan","count=="+count)
        DisposableEffect(key1 = Unit, effect ={

            onDispose {
                Log.e("ethan","onDispose==")
            }
        } )
        Text(text = "当前页面$count$number", modifier = modifier)

    }

    //非联动方式
    @Composable
    fun BottomNav() {
//        val bottomNumber = listOf("首页", "清除", "电话")
//        val bottomIcon =
//            listOf(
//                ImageVector.vectorResource(id = R.drawable.svg to xml),
//                ImageVector.vectorResource(id = R.drawable.svg to xml),
//                ImageVector.vectorResource(id = R.drawable.svg to xml),
//            )
//        BottomNavigation() {
//            bottomNumber.forEachIndexed { index, contentText ->
//                BottomNavigationItem(
//                    modifier = Modifier.background(Color.White),
//                    selected = currentPage.value == index,
//                    onClick = {
//                        currentPage.value = index
//                    },
//                    label = {
//                        Text(
//                            text = contentText,
//                            color = if (currentPage.value == index) ThemeColor else TextGrayColor
//                        )
//                    }, icon = {
//                        Icon(imageVector = bottomIcon[index], contentDescription = null)
//                    }, selectedContentColor = ThemeColor, unselectedContentColor = TextGrayColor
//                )
//            }
//        }
    }

    @Preview
    @Composable
    fun ScaffoldBottomBarSamplesPreview() {
        ScaffoldBottomBarSamples()
    }

    override fun getActTtitle(): String {
        return this::class.java.simpleName
    }
}
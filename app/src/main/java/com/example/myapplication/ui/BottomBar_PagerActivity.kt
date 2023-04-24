package com.example.myapplication.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 * Created by Ethan Cui on 2023/1/11
 */
class BottomBar_PagerActivity : BaseActivity() {


    @Composable
    override fun ContentView() {
        ScaffoldBottomBarSamples()
    }


    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun ScaffoldBottomBarSamples() {
        val bottomNumber = listOf("首页", "清除", "电话")
        val pagerState = rememberPagerState()
        Scaffold( bottomBar = {
            BottomNav(bottomNumber,pagerState)
        }, content = { paddingValues: PaddingValues ->
            HorizontalPager(
                count = bottomNumber.size, modifier = Modifier.fillMaxHeight(),
                state = pagerState,
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
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun BottomNav(bottomNumber: List<String>,pagerState: PagerState) {
        val scope = rememberCoroutineScope()
        val bottomIcon =
            listOf(Icons.Default.Home, Icons.Default.Clear, Icons.Default.Call, Icons.Default.Email)
        BottomNavigation() {
            bottomNumber.forEachIndexed { index, contentText ->
                BottomNavigationItem(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }, label = {
                        Text(
                            text = contentText,
                            color = if (pagerState.currentPage == index) Color.Black else Color.White
                        )
                    }, icon = {
//                            if (checkIndex == index) {
//                                BadgeBox(badgeContent = {
//                                    //显示消息气泡99+
//                                    Text(text = "99+")
//                                }, contentColor = Color.White, backgroundColor = Color.Red) {
//                                    Icon(imageVector = bottomIcon[index], contentDescription = null)
//                                }
//                            } else {
                        Icon(imageVector = bottomIcon[index], contentDescription = null)
//                            }
                    }, selectedContentColor = Color.Black, unselectedContentColor = Color.White
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

    @Preview
    @Composable
    fun ScaffoldBottomBarSamplesPreview() {
        ScaffoldBottomBarSamples()
    }

    override fun getActTtitle(): String {
        return this::class.java.simpleName
    }
}
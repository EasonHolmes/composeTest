package com.example.myapplication.ui

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

class ShareTransitionActivity : BaseActivity() {
    @Composable
    override fun ContentView() {
            Screen()
    }
    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun Screen() {
        var isShowDetail by remember { mutableStateOf(true) }
        //使用共享元素布局
        SharedTransitionLayout {
            //将界面切换动画交给系统处理
            AnimatedContent(targetState = isShowDetail) { targetState ->
                if (targetState) {
                    PageOne(
                        onClick = { isShowDetail = false },
                        //共享作用域传递给子布局，为了使用sharedElement
                        sharedTransitionScope = this@SharedTransitionLayout,
                        //动画作用于传递给子布局，用于协调动画
                        animatedVisibilityScope = this@AnimatedContent
                    )
                } else {
                    PageTwo(
                        onClick = { isShowDetail = true },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedContent
                    )
                }
            }
        }
    }
    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun PageOne(
        onClick: () -> Unit,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() },
            contentAlignment = Alignment.TopStart   //图在左上角
        ) {
            with(sharedTransitionScope) {
                Image(
                    painter = painterResource(R.mipmap.caodi_icon),
                    contentDescription = null,
                    modifier = Modifier.sharedElement(  //sharedElement标记用于共享的元素
                        state = rememberSharedContentState(key = "image"),  //key用于区分和匹配元素
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun PageTwo(
        onClick: () -> Unit,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            with(sharedTransitionScope) {
                Image(
                    painter = painterResource(R.mipmap.caodi_icon),
                    contentDescription = null,
                    modifier = Modifier.sharedElement(    //sharedElement标记用于共享的元素
                        state = rememberSharedContentState(key = "image"),    //key用于区分和匹配元素
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                )
            }
        }
    }
}
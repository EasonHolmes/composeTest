package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.myapplication.R
import com.example.myapplication.ui.mytheme.LightDarkTheme
import com.example.myapplication.ui.widget.backTopbar
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Created by Ethan Cui on 2022/11/8
沉浸式方法
step1:
使用把布局顶上去WindowCompat.setDecorFitsSystemWindows(window, false) (这个方法是没有透明遮罩)
或
<item name="android:windowTranslucentStatus">true</item> (这个方法有)

step2:
使用
//设置状态栏透明
rememberSystemUiController().run {
setStatusBarColor(Color.Transparent, false)
//                        setSystemBarsColor(colorResource(id = R.color.teal_200), false)
//                        setNavigationBarColor(colorResource(id = R.color.teal_200), false)
}

Step3:

//使用ProvideWindowInsets 包裹根布局这样才能获取真实状态栏高度
ProvideWindowInsets {
scaffold(){}
}
step4:
//使用Spacer占位状态栏
Spacer(
modifier = Modifier
.background(color)
.statusBarsHeight()//设置状态栏高度
.fillMaxWidth()
)
 *
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_200)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            rememberSystemUiController().run {
                setStatusBarColor(Color.Transparent, false)
//                        setSystemBarsColor(colorResource(id = R.color.teal_200), false)
//                        setNavigationBarColor(colorResource(id = R.color.teal_200), false)
            }
            LightDarkTheme {
                ProvideWindowInsets {
                    if (needScaffold()) {
                        Scaffold(topBar = {
                            backTopbar(
                                title = getActTtitle(),
                                backClick = {
                                    finish()
                                })
                        },
                            content = { paddingValues ->
                                paddingValues
                                ContentView()
                            }
                        )
                    } else {
                        Column() {
                            Spacer(
                                modifier = Modifier
                                    .background(MaterialTheme.colors.onPrimary)
                                    .statusBarsHeight()//设置状态栏高度
                                    .fillMaxWidth()
                            )
                            ContentView()
                        }
                    }
                }
            }
        }
    }

    protected open fun needScaffold(): Boolean = true

    @Composable
    abstract fun ContentView()

    open fun getActTtitle(): String = this::class.java.simpleName
}
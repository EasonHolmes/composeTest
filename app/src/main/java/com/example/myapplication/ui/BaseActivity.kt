package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.mytheme.LightDarkTheme
import com.example.myapplication.ui.widget.backTopbar
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Created by Ethan Cui on 2022/11/8
沉浸式方法
step1:
使用把布局顶上去WindowCompat.setDecorFitsSystemWindows(window, false) (这个方法是没有透明遮罩，并且需要处理导航栏)
或
<item name="android:windowTranslucentStatus">true</item> (这个方法有，有些没有，但这个只顶状态栏，不需要处理导航栏问题)

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
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            rememberSystemUiController().run {
                setStatusBarColor(Color.Transparent, false)
//                        setSystemBarsColor(colorResource(id = R.color.teal_200), false)
//                        setNavigationBarColor(colorResource(id = R.color.teal_200), false)
            }
            LightDarkTheme {
                ProvideWindowInsets {
                    if (needTopbar()) {
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
//                        Column() {
//                            Spacer(
//                                modifier = Modifier
//                                    .background(MaterialTheme.colors.onPrimary)
//                                    .statusBarsHeight()//设置状态栏高度
//                                    .fillMaxWidth()
//                            )
                            ContentView()
//                        }
                    }
                }
            }
        }
    }

    protected open fun needTopbar(): Boolean = true

    @Composable
    abstract fun ContentView()

    protected open fun getActTtitle(): String = this::class.java.simpleName
}
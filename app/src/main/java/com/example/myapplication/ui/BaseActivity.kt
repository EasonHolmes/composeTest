package com.example.myapplication.ui

import android.graphics.Rect
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.example.myapplication.ui.mytheme.LightDarkTheme
import com.example.myapplication.ui.widget.backTopbar
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController


/**
 * Created by Ethan Cui on 2022/11/8
沉浸式方法
step1:
使用把布局顶上去WindowCompat.setDecorFitsSystemWindows(window, false) (这个方法是没有透明遮罩，并且需要处理导航栏)
或
<item name="android:windowTranslucentStatus">true</item> (这个方法有，有些没有，但这个只顶状态栏，不需要处理导航栏问题)

step1:
使用
//设置状态栏透明
rememberSystemUiController().run {
setStatusBarColor(Color.Transparent, false)//使用这种设置透明会是纯透明，上面style设置会是有遮罩
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
//        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
//        setStatusBarColor和R.attr#statusBarColor已被弃用，并且对 Android 15 没有影响。
//        setStatusBarContrastEnforced和 R.attr#statusBarContrastEnforced已被弃用，但仍对 Android 15 有影响。
//        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_200)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            rememberSystemUiController().run {
//                setStatusBarColor(Color.Transparent, false)
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
                        Scaffold { paddingValues ->
                            paddingValues
                            Column() {
                                Spacer(
                                    modifier = Modifier
//                                        .height(LocalDensity.current.run { (getStatusHeight()).toDp() })
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
    }

    private fun getStatusHeight(): Int {
        val rectangle = Rect()
        val window = window
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }

    protected open fun needTopbar(): Boolean = getActTtitle().isNotEmpty()

    @Composable
    abstract fun ContentView()

    protected open fun getActTtitle(): String = this::class.java.simpleName
}
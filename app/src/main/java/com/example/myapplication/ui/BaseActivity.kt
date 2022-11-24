package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.ui.mytheme.CustomApplicationTheme
import com.example.myapplication.ui.widget.backTopbar

/**
 * Created by Ethan Cui on 2022/11/8
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_200)
        setContent {
            CustomApplicationTheme {
                if (needScaffold()) {
                    Scaffold(topBar = {
                        backTopbar(
                            title = getActTtitle(),
                            imgResource = R.mipmap.ic_back_black,
                            backClick = {
                                finish()
                            })
                    },
                        content = { paddingValues ->
                            paddingValues
//                        Box(modifier = Modifier.padding(paddingValues)) {
                            ContentView()
//                        }
                        }
                    )
                }else{
                    ContentView()
                }
            }
        }
    }

    protected open fun needScaffold(): Boolean = true

    @Composable
    abstract fun ContentView()

    abstract fun getActTtitle(): String
}
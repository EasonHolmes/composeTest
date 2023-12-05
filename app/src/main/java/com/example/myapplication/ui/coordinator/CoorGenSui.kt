package com.example.myapplication.ui.coordinator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import com.example.myapplication.R

class CoorGenSui : AppCompatActivity() {
    private val type by lazy {
        intent.getIntExtra("type", 0)
    }

    companion object {
        fun startAct(context: Context, type: Int) {
            context.startActivity(Intent(context, CoorGenSui::class.java).apply {
                putExtra("type", type)
            })
        }
    }

    //appbarlayout中有一个自定义
//    app:layout_behavior=".ui.widget.AppBarLayoutBehavior"
    //解决解决appbarLayout若干问题
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            when (type) {
                0 -> {
                    R.layout.coordinatorlayout_compose
                }
                1->{
                    R.layout.coordinatorlayout_compose2
                }
                else -> {
                    R.layout.coordinatorlayout_compose2
                }

            }
        )
        findViewById<ComposeView>(R.id.compose_view).apply {
            setContent {
                val nestedScrollInterop = rememberNestedScrollInteropConnection()
                LazyColumn(content = {
                    items(100) {
                        Text(text = "items$it")
                    }
                }, modifier = Modifier.nestedScroll(nestedScrollInterop))
            }
        }
    }
}
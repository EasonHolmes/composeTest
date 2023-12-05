package com.example.myapplication.ui.coordinator

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.R
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight

class CoordinatorLayout2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideWindowInsets {
                Column {
                    Spacer(modifier = Modifier.statusBarsHeight())
                    Button(onClick = {
                        CoorGenSui.startAct(this@CoordinatorLayout2Activity,0)
                    }) {
                        Text(text = "跟随滑动")
                    }
                    Button(onClick = {
                        CoorGenSui.startAct(this@CoordinatorLayout2Activity,1)
                    }) {
                        Text(text = "吸顶")
                    }
                }
            }

        }

    }

    @Composable
    private fun ShareType1UI() {

    }


}

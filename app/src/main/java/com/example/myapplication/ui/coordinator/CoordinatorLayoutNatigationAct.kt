package com.example.myapplication.ui.coordinator

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.ui.BaseActivity

class CoordinatorLayoutNatigationAct : BaseActivity() {
    @Composable
    override fun ContentView() {
        Column {
            Button(onClick = {
                startActivity(Intent(this@CoordinatorLayoutNatigationAct,
                    CoordinatorLayoutActivity::class.java))
            }) {
                Text(text = "Material3自带的，仅有topappbar,以及判断向上向下滑，来做动画")
            }
            Button(onClick = {
                startActivity(Intent(this@CoordinatorLayoutNatigationAct,
                    CoordinatorLayout2Activity::class.java))
            }) {
                Text(text = "androidview+compose的方式")
            }

        }
    }

}
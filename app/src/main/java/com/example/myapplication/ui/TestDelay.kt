package com.example.myapplication.ui

import android.os.Handler
import kotlinx.coroutines.flow.flow

/**
 * Created by Ethan Cui on 2022/10/28
 */
class TestDelay {
    fun delayTest(callback: (text :String) -> Unit){
        Handler().postDelayed({callback("Color to Change")},2000)
    }
    suspend fun delayTestLaunch(mill:Long = 2000,callback: (text :String) -> Unit){
        Handler().postDelayed({callback("Color to Change")},mill)
    }
}
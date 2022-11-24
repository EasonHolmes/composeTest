package com.example.myapplication.ui

import android.os.Environment
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.myapplication.R
import com.example.myapplication.ui.widget.drawContent
import java.io.File

/**
 * Created by Ethan Cui on 2022/11/22
 */
class AnimationActivity : BaseActivity() {

    @Composable
    override fun ContentView() {
        drawContent(imgId = R.drawable.ic_launcher_foreground, this)
        Log.e(
            "ethan",
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/rx_mode.tag").exists()
                .toString()
        )
    }

    override fun needScaffold(): Boolean {
        return false
    }

    override fun getActTtitle(): String {
        return "Animation"
    }
}
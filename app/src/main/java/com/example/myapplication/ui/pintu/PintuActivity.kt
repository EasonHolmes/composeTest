package com.example.myapplication.ui.pintu

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.R
import com.example.myapplication.ui.widget.PinTuWidget

/**
 * Created by Ethan Cui on 2023/4/27
 */
class PintuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                AndroidView(factory = {
                    LayoutInflater.from(it).inflate(R.layout.activity_pintu,null)
                })
                PinTuWidget(imgResources = R.drawable.aa, columns = 3)
            }
        }
    }
}
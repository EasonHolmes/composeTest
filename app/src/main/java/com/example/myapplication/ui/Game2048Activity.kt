package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.ui.p2048.ui.GameScreen
import com.example.myapplication.ui.p2048.viewmodel.GameViewModel

class Game2048Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen(gameViewModel = GameViewModel(this))
        }
    }
}

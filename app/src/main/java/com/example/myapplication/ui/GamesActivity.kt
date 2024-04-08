package com.example.myapplication.ui

import android.content.Intent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

enum class GameJumpEntity(val value: String) {
    PINTU("拼图游戏xml和compose"),
    Game2048("2048"),
    WHACKMOLE("打地鼠"),
}

class GamesActivity : BaseActivity() {

    private val listData = mutableListOf(
        GameJumpEntity.PINTU,
        GameJumpEntity.Game2048,
        GameJumpEntity.WHACKMOLE
    )

    @Composable
    override fun ContentView() {
        LazyColumn(content = {
            itemsIndexed(items = listData, itemContent = { index, item ->
                Button(onClick = {
                    when (item) {
                        GameJumpEntity.Game2048 -> {
                            startActivity(Intent(this@GamesActivity, Game2048Activity::class.java))
                        }

                        GameJumpEntity.WHACKMOLE -> {
                            startActivity(Intent(this@GamesActivity, WhackMoleActivity::class.java))
                        }
                        GameJumpEntity.PINTU -> {
                            startActivity(Intent(this@GamesActivity, PintuActivity::class.java))
                        }
                    }
                }) {
                    Text(text = item.value)
                }
            })
        })
    }
}
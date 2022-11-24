package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.widget.CollapsingEffectScreen

/**
 * Created by Ethan Cui on 2022/11/24
 */
class CollapsingActiivty : BaseActivity() {
    @Composable
    override fun ContentView() {
        val items = (1..100).map { "Item $it" }.toMutableList()
        CollapsingEffectScreen(items = items, header = {
            Image(
                painter = painterResource(id = R.mipmap.toolbar_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }, itemContent = { index, item ->
            Text(
                text = item,
                Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        })
    }

    override fun getActTtitle(): String {
        return "collapsing"
    }
}
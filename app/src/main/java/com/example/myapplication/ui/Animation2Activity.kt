package com.example.myapplication.ui

import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow


/**
 * Created by Ethan Cui on 2023/5/12
 */
class Animation2Activity : BaseActivity() {

    @Composable
    override fun ContentView() {
        Column {
            ScrollText()
        }
    }

    @Composable
    private fun ScrollText() {
        var target by remember {
            mutableStateOf(resources.displayMetrics.widthPixels+120)
        }
        val anima by animateSizeAsState(
            targetValue = Size(target.toFloat(), 0f),
            animationSpec = infiniteRepeatable(tween(durationMillis = 5000, easing = LinearEasing), repeatMode = RepeatMode.Restart),)
        Box(modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {

            }) {
            Text(
                text = "asdfa", modifier = Modifier
                    .offset(x = LocalDensity.current.run { (anima.width).toDp()}),
                overflow = TextOverflow.Clip,
                softWrap = false,
                onTextLayout = {
                    target =  -it.size.width
                }
            )
        }
    }
}
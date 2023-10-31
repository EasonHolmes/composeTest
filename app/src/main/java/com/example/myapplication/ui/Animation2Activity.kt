package com.example.myapplication.ui

import android.graphics.drawable.Icon
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import com.example.myapplication.ui.widget.ChangeStatusCheckBox
import com.example.myapplication.ui.widget.SwitchDefaults
import com.example.myapplication.ui.widget.SwitchMaterial3


/**
 * Created by Ethan Cui on 2023/5/12
 */
class Animation2Activity : BaseActivity() {

    @Composable
    override fun ContentView() {
        Column(Modifier.fillMaxSize().background(Color.Gray)) {
            ScrollText()
            FounctionMaterial3()
            ChangeStatusCheckBox()
        }
    }

    @Composable
    private fun ScrollText() {
        var target by remember {
            mutableStateOf(resources.displayMetrics.widthPixels + 120)
        }
        val anima by animateSizeAsState(
            targetValue = Size(target.toFloat(), 0f),
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "",
        )
        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    target = -it.size.width
                }
                .offset(x = LocalDensity.current.run { (anima.width).toDp() }),
        ) {
            Text(
                text = "asdfa", modifier = Modifier,
//                    .offset(x = LocalDensity.current.run { (anima.width).toDp()}),
                overflow = TextOverflow.Clip,
                softWrap = false,
                onTextLayout = {
//                    target =  -it.size.width
                }
            )
        }
    }

    @Composable
    private fun FounctionMaterial3() {
        var checked by remember {
            mutableStateOf(false)
        }
        SwitchMaterial3(
            checked = checked,
            onCheckedChange = { checked = it },
            thumbContent = {
                Icon(imageVector = Icons.Default.Check, contentDescription = "")
            },
            colors = SwitchDefaults.colors(
                uncheckedBorderColor = Color.Green,
                checkedBorderColor = Color.Red,
                checkedTrackColor = Color.Cyan,
                uncheckedTrackColor = Color.Blue
                , uncheckedThumbColor = Color.White,
//                checkedThumbColor = Color.White,
            )
        )
    }

}
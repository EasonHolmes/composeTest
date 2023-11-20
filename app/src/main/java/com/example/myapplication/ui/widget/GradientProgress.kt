package com.example.myapplication.ui.widget

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//渐变带动画progress
@Composable
fun GradientProgress(
    modifier: Modifier = Modifier,
    progressPadding: PaddingValues = PaddingValues(),
    progressColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent,
    horizontalGradient: Brush = Brush.horizontalGradient(
        listOf(
            Color.Transparent,
            Color.Transparent
        )
    ),
    progress: Float,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(16.dp),
    tween: TweenSpec<Dp> = tween(500, delayMillis = 300, easing = LinearOutSlowInEasing),
    anim: Boolean = false
) {
    val animaWidth = remember {
        Animatable(0.dp, Dp.VectorConverter)
    }
    val localDensity = LocalDensity.current
    var targetWidth by remember {
        mutableStateOf(0.dp)
    }
    Box(
        modifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .height(intrinsicSize = IntrinsicSize.Min)
                .background(backgroundColor, roundedCornerShape)
                .onSizeChanged {
                    targetWidth = with(localDensity) {
                        (it.width).toDp()
                    }
                    Log.e("ethan", "siiii==" + it.width)
                }
                .padding(progressPadding), contentAlignment = Alignment.CenterStart
        ) {
            val modifierBg = if (progressColor != Color.Transparent) {
                Modifier.background(progressColor, roundedCornerShape)
            } else {
                Modifier.background(horizontalGradient, roundedCornerShape)
            }
            val progressModifier = if (anim) {
                Modifier
                    .fillMaxHeight()
                    .width(width = animaWidth.value)
            } else {
                Modifier
                    .fillMaxHeight()
                    .width(targetWidth * progress)
            }
            Spacer(
                modifier = progressModifier
                    .then(modifierBg)
            )
        }
    }
    LaunchedEffect(key1 = Unit, block = {
        if (anim) {
            animaWidth.animateTo(
                targetWidth * progress,
                tween
            )
        }
    })

}
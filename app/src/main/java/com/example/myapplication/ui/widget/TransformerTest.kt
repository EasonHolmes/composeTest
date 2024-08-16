package com.example.myapplication.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun TransformBox() {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var rotationAngle by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }

    Box(modifier = Modifier
        .size(80.dp)
        .rotate(rotationAngle) // 需要注意 offset 与 rotate 的调用先后顺序 不需要旋转可注释
        .offset {//不需要随意移动就注释这个方法就好
            IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
        }
        .scale(scale)
        .background(Color.LightGray)
//        .pointerInput(Unit) {
            //这个方法与下面transformable是一样的效果，不同的是detectTransformGestures可以支持单指拖动手势回调
            //transformable只能监听到双指拖动，按需使用
//            detectTransformGestures(panZoomLock = false,//当拖动或缩放手势发生时是否支持旋转 true锁定不支持 false不锁定支持
//                onGesture = {centroid: Offset, panChange: Offset, zoomChange: Float, rotationChange: Float->
//                    scale *= zoomChange
//                    offset += panChange
//                    rotationAngle += rotationChange
//                })
//        },
        .transformable(
            state = rememberTransformableState { zoomChange: Float, panChange: Offset, rotationChange: Float ->
                scale *= zoomChange
                offset += panChange
                rotationAngle += rotationChange
            }
        ),
        contentAlignment = Alignment.Center
    ){
        Text(text = "双指旋转缩放拖动")
    }
}
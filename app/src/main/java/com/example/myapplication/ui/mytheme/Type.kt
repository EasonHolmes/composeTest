package com.example.myapplication.ui.mytheme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Created by Ethan Cui on 2022/3/11
 */
val typography = Typography(
    body1 = TextStyle(
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        color = Color.Blue
    )
)
val Shapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)
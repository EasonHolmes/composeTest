package com.example.myapplication.ui.mytheme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import com.example.myapplication.ui.theme.*

/**
 * Created by Ethan Cui on 2022/3/11
 */

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

// 动态配色适用于 Android 12 及更高版本的系统
//val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
//val colorScheme = when {
//    dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
//    dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
//    darkTheme -> darkColorScheme(...)
//    else -> lightColorScheme(...)
//}
//
//MaterialTheme(
//colorScheme = colorScheme,
//typography = typography,
//shapes = shapes
//) {
//    // M3 应用内容

@Composable
fun CustomApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
)
{
   val  colors = if (darkTheme) DarkColorPalette else LightColorPalette
   MaterialTheme(
       colors = colors,
       typography = typography,
       shapes = Shapes,
       content = content,
   )
}
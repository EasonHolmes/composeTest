package com.example.myapplication.ui.mytheme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.theme.*

/**
 * Created by Ethan Cui on 2022/3/11
 */

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    onPrimary = Teal2000,
    background = Color.Black,

)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    onPrimary = Purple200,
    background = Color.White,

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

//lightDarkTheme根据主题变化
@Composable
fun LightDarkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
//    CompositionLocalProvider(LocalContentColor provides  Color.Green) {
    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content,
    )
//    }

}

/**
 * 手动更改色值圆角这些步骤：
 * 1。定义枚举或实体类
 * 2。MaterialTheme中传入对应的实体，以应用自义定的颜色
 * 3。使用可变类型比如： var changeColor by remember {
mutableStateOf(ColorTheme.BLACK)
}
4。在使用色值或圆角等的时候使用MaterialTheme.colors.onBackground
5。在触发修改的位置更改changeColor(枚举值)即可
 */
//还有lightDarkTheme根据主题变化
@Composable
fun ChangeColorApplicationTheme(
    colorTheme: ColorTheme = ColorTheme.WHITE,
    content: @Composable () -> Unit
) {
    val colors = when (colorTheme) {
        ColorTheme.BLACK -> {
            Color.Black
        }
        ColorTheme.GREEN -> {
            Color.Green
        }
        ColorTheme.BLUE -> {
            Color.Blue
        }
        ColorTheme.WHITE -> {
            Color.White
        }
    }
    MaterialTheme(
        colors = lightColors(onBackground = colors, primary = Teal200),
        typography = typography,
        shapes = Shapes,
        content = content,
    )
}
//用来控制重组范围,自定义一个非inline且没有返回值的compose
@Composable
fun ControlReComposition(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier){
        content.invoke()
    }
}

enum class ColorTheme {
    BLACK,
    GREEN,
    BLUE,
    WHITE
}
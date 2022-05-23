package com.wo1f.chatapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = DarkBlue,
    primaryVariant = DarkBlue1,
    onPrimary = Color.White,
    secondary = LightYellowGreen,
    secondaryVariant = LightYellowGreen1,
    onSecondary = Color.White,
    background = DarkBlue,
    onBackground = Color.White,
    surface = DarkBlue1,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = DarkBlue,
    primaryVariant = DarkBlue1,
    onPrimary = Color.Black,
    secondary = LightYellowGreen,
    secondaryVariant = LightYellowGreen1,
    onSecondary = Color.Black,
    background = Black,
    onBackground = Black,
    surface = Color.White,
    onSurface = LightGray
)

@Composable
fun ChatAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

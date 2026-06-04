package com.example.take_note_app_2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PurplePrimary,
    secondary = GoldSecondary,
    tertiary = WhiteBackground,
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
)

private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    secondary = GoldSecondary,
    tertiary = PurpleDark,
    background = WhiteBackground,
    surface = WhiteBackground,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
)

@Composable
fun Takenoteapp2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set dynamicColor to false to use our custom Purple/Gold theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

package com.epamupskills.booknotes.ui.theme

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
    primary = BlueGrey900,
    secondary = BlueGrey700,
    tertiary = BlueGrey800,
    background = BlueGrey900,
    surface = BlueGrey900,
    onPrimary = Color(0xFFECEFF1),
    onSecondary = Color(0xFF90A4AE),
    onTertiary = Color(0xFFCFD8DC),
    onBackground = Color(0xFFECEFF1),
    onSurface = Color(0xFFECEFF1),
    primaryContainer = BlueGrey900,
)

private val LightColorScheme = lightColorScheme(
    primary = BlueGrey900,
    secondary = BlueGrey700,
    tertiary = BlueGrey800,
    background = BlueGrey900,
    surface = BlueGrey900,
    onPrimary = Color(0xFFECEFF1),
    onSecondary = Color(0xFF90A4AE),
    onTertiary = Color(0xFFCFD8DC),
    onBackground = Color(0xFFECEFF1),
    onSurface = Color(0xFFECEFF1),
    primaryContainer = BlueGrey900,
)

@Composable
fun BookNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
package com.epamupskills.booknotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
    secondaryContainer = BlueGrey800,
)

private val LightColorScheme = lightColorScheme(
    primary = BlueGrey900,
    secondary = BlueGrey700,
    tertiary = BlueGrey800,
    background = Color(0xFFECEFF1),
    surface = BlueGrey900,
    onPrimary = Color(0xFFECEFF1),
    onSecondary = Color(0xFF90A4AE),
    onTertiary = Color(0xFFCFD8DC),
    onBackground = Color(0xFFECEFF1),
    onSurface = Color(0xFFECEFF1),
    secondaryContainer = BlueGrey800,
)

@Composable
fun BookNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
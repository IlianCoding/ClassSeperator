package com.app.assignmentandroidapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = White,
    onPrimary = White,
    surface = SmoothPurple
)

/* Dark color scheme */
private val DarkColorScheme = darkColorScheme(
    primary = DeepNavy,
    onPrimary = DeepNavy
)

@Composable
fun AssignmentAndroidApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
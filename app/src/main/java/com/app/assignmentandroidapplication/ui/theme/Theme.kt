package com.app.assignmentandroidapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Cinerous,
    onPrimary = Color.White,
    surface = Color.White,
    background = LightBrown,
    onSurface = Charcoal
)

private val DarkColorScheme = darkColorScheme(
    primary = LightTeal,
    onPrimary = Color.Black,
    primaryContainer = PastelBlueDark,
    secondary = SoftGray,
    onSecondary = OffWhite,
    secondaryContainer = MutedLavender,
    background = DeepNavy,
    onBackground = OffWhite,
    surface = DarkTeal,
    onSurface = LightGrayDark,
    error = LightCoral,
    onError = Color.Black,
    surfaceVariant = MediumGrayDarkBorder,
    onSurfaceVariant = SoftBlueGray
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
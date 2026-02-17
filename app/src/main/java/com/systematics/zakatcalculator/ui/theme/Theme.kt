package com.systematics.zakatcalculator.ui.theme

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
    primary = PrimaryGreen,
    onPrimary = White,
    primaryContainer = DarkGreen,
    onPrimaryContainer = LightGreen,
    secondary = SecondaryGreen,
    onSecondary = Black,
    tertiary = GoldColor,
    onTertiary = Black,
    tertiaryContainer = Color(0xFF4E3F12),
    background = Color(0xFF0F1613),
    surface = Color(0xFF121A16),
    onSurface = White,
    surfaceVariant = Color(0xFF2B332A),
    onSurfaceVariant = LightGreen
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = White,
    primaryContainer = GradientYellow,
    onPrimaryContainer = DarkGreen,
    secondary = SecondaryGreen,
    onSecondary = White,
    tertiary = GoldColor,
    onTertiary = Black,
    tertiaryContainer = Color(0xFFF4E5C2),
    background = Color(0xFFF5F5F5),
    surface = White,
    onSurface = Black,
    surfaceVariant = VerseBackground,
    onSurfaceVariant = VerseText

    /* Other default colors to override
    onBackground = Color(0xFF1C1B1F),
    */
)

@Composable
fun ZakatCalculatorTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
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

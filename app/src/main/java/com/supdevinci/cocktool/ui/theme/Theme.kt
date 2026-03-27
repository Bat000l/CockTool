package com.supdevinci.cocktool.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🍹 THÈME SOMBRE (CockTail Bar Night)
private val DarkColorScheme = darkColorScheme(
    primary = WarmOrange80,              // Orange clair principal
    onPrimary = DarkBackground,          // Texte sombre sur orange
    primaryContainer = WarmOrange40,     // Container orange foncé
    onPrimaryContainer = WarmOrange80,
    
    secondary = WarmRust80,              // Rust/Terracotta clair
    onSecondary = DarkBackground,
    secondaryContainer = WarmRust40,
    onSecondaryContainer = WarmRust80,
    
    tertiary = WarmOrange80,             // Même que primaire pour cohérence
    onTertiary = DarkBackground,
    tertiaryContainer = WarmOrange40,
    onTertiaryContainer = WarmOrange80,
    
    error = Color(0xFFFF6B6B),
    errorContainer = Color(0xFFFFEBEE),
    onError = DarkBackground,
    
    background = DarkBackground,
    onBackground = TextLight,
    surface = DarkSurface,
    onSurface = TextLight,
    surfaceVariant = Color(0xFF3D3430),
    onSurfaceVariant = TextLight
)

// 🍸 THÈME CLAIR (CockTail Bar Day)
private val LightColorScheme = lightColorScheme(
    primary = WarmOrange40,              // Orange foncé principal
    onPrimary = Color(0xFFFFFFFF),       // Texte blanc sur orange
    primaryContainer = WarmOrange80,     // Container orange clair
    onPrimaryContainer = WarmOrange40,
    
    secondary = WarmRust40,              // Rust foncé secondaire
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = WarmRust80,
    onSecondaryContainer = WarmRust40,
    
    tertiary = WarmOrange40,             // Même que primaire pour cohérence
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = WarmOrange80,
    onTertiaryContainer = WarmOrange40,
    
    error = Color(0xFFDC2626),
    errorContainer = Color(0xFFFFEBEE),
    onError = Color(0xFFFFFFFF),
    
    background = LightBackground,
    onBackground = TextDark,
    surface = LightSurface,
    onSurface = TextDark,
    surfaceVariant = Color(0xFFEEE0D0),
    onSurfaceVariant = TextDark
)

@Composable
fun CockToolTheme(
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
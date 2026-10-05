package com.veda.vedahostelstudents.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkMaterialColorScheme = darkColorScheme(
    primary = FigmaDarkAccent,
    onPrimary = FigmaDarkSurface,
    primaryContainer = FigmaDarkRaised,
    onPrimaryContainer = FigmaDarkInk,
    secondary = FigmaDarkAccent,
    onSecondary = FigmaDarkSurface,
    secondaryContainer = FigmaDarkAccentSoft,
    onSecondaryContainer = FigmaDarkInk,
    tertiary = FigmaDarkAccent,
    background = FigmaDarkCanvas,
    onBackground = FigmaDarkInk,
    surface = FigmaDarkSurface,
    onSurface = FigmaDarkInk,
    surfaceVariant = FigmaDarkRaised,
    onSurfaceVariant = FigmaDarkMuted,
    outline = FigmaDarkBorder,
    outlineVariant = FigmaDarkBorder,
    error = FigmaDarkRed,
    onError = FigmaDarkInk
)

private val LightMaterialColorScheme = lightColorScheme(
    primary = FigmaLightAccent,
    onPrimary = FigmaLightSurface,
    primaryContainer = FigmaLightRaised,
    onPrimaryContainer = FigmaLightInk,
    secondary = FigmaLightAccent,
    onSecondary = FigmaLightSurface,
    secondaryContainer = FigmaLightAccentSoft,
    onSecondaryContainer = FigmaLightInk,
    tertiary = FigmaLightAccent,
    background = FigmaLightCanvas,
    onBackground = FigmaLightInk,
    surface = FigmaLightSurface,
    onSurface = FigmaLightInk,
    surfaceVariant = FigmaLightRaised,
    onSurfaceVariant = FigmaLightMuted,
    outline = FigmaLightBorder,
    outlineVariant = FigmaLightBorder,
    error = FigmaLightRed,
    onError = FigmaLightSurface
)

@Composable
fun VEDAHOSTELSTUDENTSTheme(
    appearancePreference: String = "System",
    content: @Composable () -> Unit
) {
    val darkTheme = when (appearancePreference.lowercase()) {
        "light" -> false
        "dark" -> true
        else -> isSystemInDarkTheme()
    }

    val vedaColors = if (darkTheme) DarkVedaColors else LightVedaColors
    val materialColorScheme = if (darkTheme) DarkMaterialColorScheme else LightMaterialColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = vedaColors.canvas.toArgb()
            window.navigationBarColor = vedaColors.canvas.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalVedaColors provides vedaColors,
        LocalVedaTypography provides VedaTypographyInstance,
        LocalVedaSpacing provides VedaSpacingInstance,
        LocalVedaShapes provides VedaShapesInstance
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = Typography,
            shapes = MaterialShapes,
            content = content
        )
    }
}

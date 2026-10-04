package com.veda.vedahostelstudents.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val VedaColorScheme = darkColorScheme(
    primary = VedaPrimaryBlue,
    onPrimary = VedaTextPrimary,
    primaryContainer = VedaDarkSurfaceVariant,
    onPrimaryContainer = VedaTextPrimary,
    secondary = VedaBrightBlue,
    onSecondary = VedaTextPrimary,
    secondaryContainer = VedaDarkSurfaceHighlight,
    onSecondaryContainer = VedaTextPrimary,
    tertiary = VedaLightBlue,
    background = VedaDarkBackground,
    onBackground = VedaTextPrimary,
    surface = VedaDarkSurface,
    onSurface = VedaTextPrimary,
    surfaceVariant = VedaDarkSurfaceVariant,
    onSurfaceVariant = VedaTextSecondary,
    outline = VedaBorderSubtle,
    outlineVariant = VedaDivider,
    error = VedaAlertRed,
    onError = VedaTextPrimary
)

@Composable
fun VEDAHOSTELSTUDENTSTheme(
    darkTheme: Boolean = true, // Default to VEDA premium dark navy identity
    content: @Composable () -> Unit
) {
    val colorScheme = VedaColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

package com.veda.vedahostelstudents.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.R

// ==============================================================================
// TYPOGRAPHY SYSTEM (EXACT REFERENCE SPECS - PAGE 46)
// ==============================================================================

// Bundled Inter Font Family (Offline / On-Device Resource)
val InterFontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

data class VedaTypography(
    // Display / 32 - 750 · 115%
    val display: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.5).sp
    ),
    // Title / 28 · 700 · 125%
    val screenTitle: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.25).sp
    ),
    // Section / 20 · 650 · 140%
    val sectionTitle: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    // Body / 15 · 400 · 145%
    val body: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    ),
    // Supporting / 13 · 400 · 150%
    val bodySecondary: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.25.sp
    ),
    // Label / 14 · 500
    val label: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    // Button / 15 · 700
    val button: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    ),
    // Overline / Caption / 11 · 700
    val caption: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    // Navigation / 11 · 700
    val navigation: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

val VedaTypographyInstance = VedaTypography()

val LocalVedaTypography = staticCompositionLocalOf { VedaTypographyInstance }

val Typography = Typography(
    displayLarge = VedaTypographyInstance.display,
    titleLarge = VedaTypographyInstance.screenTitle,
    titleMedium = VedaTypographyInstance.sectionTitle,
    bodyLarge = VedaTypographyInstance.body,
    bodyMedium = VedaTypographyInstance.bodySecondary,
    labelLarge = VedaTypographyInstance.label,
    labelMedium = VedaTypographyInstance.navigation,
    labelSmall = VedaTypographyInstance.caption
)

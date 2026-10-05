package com.veda.vedahostelstudents.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ==============================================================================
// FIGMA DESIGN SYSTEM TOKENS (EXACT REFERENCE SPECS - PAGE 48)
// ==============================================================================

// --- DAYLIGHT / LIGHT FIGMA TOKENS ---
val FigmaLightCanvas = Color(0xFFF4F6FA)
val FigmaLightSurface = Color(0xFFFFFFFF)
val FigmaLightRaised = Color(0xFFEAF0FB)
val FigmaLightBorder = Color(0xFFDFE6F0)
val FigmaLightAccent = Color(0xFF355C9D)
val FigmaLightAccentSoft = Color(0xFFE9EFFD)
val FigmaLightInk = Color(0xFF0D172A)
val FigmaLightMuted = Color(0xFF66748C)
val FigmaLightGreen = Color(0xFF26745B)
val FigmaLightGreenSoft = Color(0xFFE6F3EC)
val FigmaLightAmber = Color(0xFFBB681F)
val FigmaLightAmberSoft = Color(0xFFF7F0D0)
val FigmaLightRed = Color(0xFFA04850)
val FigmaLightRedSoft = Color(0xFFF0ECEC)

// --- MIDNIGHT / DARK FIGMA TOKENS ---
val FigmaDarkCanvas = Color(0xFF162338)
val FigmaDarkSurface = Color(0xFF0D172A)
val FigmaDarkRaised = Color(0xFF1E3050)
val FigmaDarkBorder = Color(0xFF2B3C58)
val FigmaDarkAccent = Color(0xFF94B2EB)
val FigmaDarkAccentSoft = Color(0xFF243863)
val FigmaDarkInk = Color(0xFFF1F5FD)
val FigmaDarkMuted = Color(0xFFA0AEC0)
val FigmaDarkGreen = Color(0xFF89CDB0)
val FigmaDarkGreenSoft = Color(0xFF1C3C38)
val FigmaDarkAmber = Color(0xFFE1C17C)
val FigmaDarkAmberSoft = Color(0xFF3A3427)
val FigmaDarkRed = Color(0xFFE6A2A6)
val FigmaDarkRedSoft = Color(0xFF3E2937)

// ==============================================================================
// LEGACY RAW COLORS (PRESERVED FOR BACKWARD COMPATIBILITY)
// ==============================================================================
val RawVedaDarkBackground = FigmaDarkCanvas
val RawVedaDarkSurface = FigmaDarkSurface
val RawVedaDarkSurfaceVariant = FigmaDarkRaised
val RawVedaDarkSurfaceHighlight = Color(0xFF283856)

val RawVedaLightBackground = FigmaLightCanvas
val RawVedaLightSurface = FigmaLightSurface
val RawVedaLightSurfaceVariant = FigmaLightRaised
val RawVedaLightSurfaceHighlight = Color(0xFFCBD5E1)

// ==============================================================================
// VEDA SEMANTIC COLOR PALETTE
// ==============================================================================

data class VedaColors(
    // Background
    val canvas: Color,
    val surface: Color,
    val raised: Color,

    // Content
    val ink: Color,
    val muted: Color,
    val disabled: Color,

    // Borders
    val border: Color,
    val divider: Color,

    // Brand / Accent
    val primary: Color,
    val secondary: Color,
    val accentSoft: Color,

    // Semantic Status
    val success: Color,
    val successSoft: Color,
    val warning: Color,
    val warningSoft: Color,
    val error: Color,
    val errorSoft: Color,

    // Attendance
    val present: Color,
    val presentSoft: Color,
    val absent: Color,
    val absentSoft: Color,
    val notMarked: Color,
    val notMarkedSoft: Color,

    // Navigation
    val navigationBackground: Color,
    val navigationSelected: Color,
    val navigationUnselected: Color,

    // Meta
    val isDark: Boolean
) {
    // --- BACKWARD COMPATIBILITY PROPERTIES ---
    val background: Color get() = canvas
    val surfaceVariant: Color get() = raised
    val surfaceHighlight: Color get() = raised
    val primaryBlue: Color get() = primary
    val brightBlue: Color get() = secondary
    val lightBlue: Color get() = secondary
    val successGreen: Color get() = success
    val successGreenBg: Color get() = successSoft
    val alertRed: Color get() = error
    val alertRedBg: Color get() = errorSoft
    val warningYellow: Color get() = warning
    val warningYellowBg: Color get() = warningSoft
    val textPrimary: Color get() = ink
    val textSecondary: Color get() = muted
    val textMuted: Color get() = muted
    val borderSubtle: Color get() = border
}

val DarkVedaColors = VedaColors(
    canvas = FigmaDarkCanvas,
    surface = FigmaDarkSurface,
    raised = FigmaDarkRaised,
    ink = FigmaDarkInk,
    muted = FigmaDarkMuted,
    disabled = Color(0xFF475569),
    border = FigmaDarkBorder,
    divider = FigmaDarkBorder,
    primary = FigmaDarkAccent,
    secondary = FigmaDarkAccent,
    accentSoft = FigmaDarkAccentSoft,
    success = FigmaDarkGreen,
    successSoft = FigmaDarkGreenSoft,
    warning = FigmaDarkAmber,
    warningSoft = FigmaDarkAmberSoft,
    error = FigmaDarkRed,
    errorSoft = FigmaDarkRedSoft,
    present = FigmaDarkGreen,
    presentSoft = FigmaDarkGreenSoft,
    absent = FigmaDarkRed,
    absentSoft = FigmaDarkRedSoft,
    notMarked = FigmaDarkAmber,
    notMarkedSoft = FigmaDarkAmberSoft,
    navigationBackground = FigmaDarkSurface,
    navigationSelected = FigmaDarkAccent,
    navigationUnselected = FigmaDarkMuted,
    isDark = true
)

val LightVedaColors = VedaColors(
    canvas = FigmaLightCanvas,
    surface = FigmaLightSurface,
    raised = FigmaLightRaised,
    ink = FigmaLightInk,
    muted = FigmaLightMuted,
    disabled = Color(0xFF94A3B8),
    border = FigmaLightBorder,
    divider = FigmaLightBorder,
    primary = FigmaLightAccent,
    secondary = FigmaLightAccent,
    accentSoft = FigmaLightAccentSoft,
    success = FigmaLightGreen,
    successSoft = FigmaLightGreenSoft,
    warning = FigmaLightAmber,
    warningSoft = FigmaLightAmberSoft,
    error = FigmaLightRed,
    errorSoft = FigmaLightRedSoft,
    present = FigmaLightGreen,
    presentSoft = FigmaLightGreenSoft,
    absent = FigmaLightRed,
    absentSoft = FigmaLightRedSoft,
    notMarked = FigmaLightAmber,
    notMarkedSoft = FigmaLightAmberSoft,
    navigationBackground = FigmaLightSurface,
    navigationSelected = FigmaLightAccent,
    navigationUnselected = FigmaLightMuted,
    isDark = false
)

val LocalVedaColors = staticCompositionLocalOf { DarkVedaColors }

object VedaTheme {
    val colors: VedaColors
        @Composable
        get() = LocalVedaColors.current

    val typography: VedaTypography
        @Composable
        get() = LocalVedaTypography.current

    val spacing: VedaSpacing
        @Composable
        get() = LocalVedaSpacing.current

    val shapes: VedaShapes
        @Composable
        get() = LocalVedaShapes.current
}

// --- DYNAMIC COMPOSABLE COLOR GETTERS ---
val VedaCanvas: Color @Composable get() = VedaTheme.colors.canvas
val VedaSurface: Color @Composable get() = VedaTheme.colors.surface
val VedaRaised: Color @Composable get() = VedaTheme.colors.raised

val VedaInk: Color @Composable get() = VedaTheme.colors.ink
val VedaMuted: Color @Composable get() = VedaTheme.colors.muted
val VedaDisabled: Color @Composable get() = VedaTheme.colors.disabled

val VedaBorder: Color @Composable get() = VedaTheme.colors.border
val VedaDivider: Color @Composable get() = VedaTheme.colors.divider

val VedaPrimary: Color @Composable get() = VedaTheme.colors.primary
val VedaSecondary: Color @Composable get() = VedaTheme.colors.secondary
val VedaAccentSoft: Color @Composable get() = VedaTheme.colors.accentSoft

val VedaSuccess: Color @Composable get() = VedaTheme.colors.success
val VedaSuccessSoft: Color @Composable get() = VedaTheme.colors.successSoft
val VedaWarning: Color @Composable get() = VedaTheme.colors.warning
val VedaWarningSoft: Color @Composable get() = VedaTheme.colors.warningSoft
val VedaError: Color @Composable get() = VedaTheme.colors.error
val VedaErrorSoft: Color @Composable get() = VedaTheme.colors.errorSoft

val VedaPresent: Color @Composable get() = VedaTheme.colors.present
val VedaPresentSoft: Color @Composable get() = VedaTheme.colors.presentSoft
val VedaAbsent: Color @Composable get() = VedaTheme.colors.absent
val VedaAbsentSoft: Color @Composable get() = VedaTheme.colors.absentSoft
val VedaNotMarked: Color @Composable get() = VedaTheme.colors.notMarked
val VedaNotMarkedSoft: Color @Composable get() = VedaTheme.colors.notMarkedSoft

val VedaNavBg: Color @Composable get() = VedaTheme.colors.navigationBackground
val VedaNavSelected: Color @Composable get() = VedaTheme.colors.navigationSelected
val VedaNavUnselected: Color @Composable get() = VedaTheme.colors.navigationUnselected

// Legacy Getters (Preserved for compatibility)
val VedaDarkBackground: Color @Composable get() = VedaTheme.colors.background
val VedaDarkSurface: Color @Composable get() = VedaTheme.colors.surface
val VedaDarkSurfaceVariant: Color @Composable get() = VedaTheme.colors.surfaceVariant
val VedaDarkSurfaceHighlight: Color @Composable get() = VedaTheme.colors.surfaceHighlight

val VedaPrimaryBlue: Color @Composable get() = VedaTheme.colors.primaryBlue
val VedaBrightBlue: Color @Composable get() = VedaTheme.colors.brightBlue
val VedaLightBlue: Color @Composable get() = VedaTheme.colors.lightBlue

val VedaSuccessGreen: Color @Composable get() = VedaTheme.colors.successGreen
val VedaSuccessGreenBg: Color @Composable get() = VedaTheme.colors.successGreenBg
val VedaAlertRed: Color @Composable get() = VedaTheme.colors.alertRed
val VedaAlertRedBg: Color @Composable get() = VedaTheme.colors.alertRedBg
val VedaWarningYellow: Color @Composable get() = VedaTheme.colors.warningYellow
val VedaWarningYellowBg: Color @Composable get() = VedaTheme.colors.warningYellowBg

val VedaTextPrimary: Color @Composable get() = VedaTheme.colors.textPrimary
val VedaTextSecondary: Color @Composable get() = VedaTheme.colors.textSecondary
val VedaTextMuted: Color @Composable get() = VedaTheme.colors.textMuted

val VedaBorderSubtle: Color @Composable get() = VedaTheme.colors.borderSubtle

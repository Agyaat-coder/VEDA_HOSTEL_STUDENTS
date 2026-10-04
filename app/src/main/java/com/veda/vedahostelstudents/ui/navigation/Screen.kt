package com.veda.vedahostelstudents.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object AccountActivation : Screen("account_activation")
    object ActivationSuccess : Screen("activation_success")
    
    // Main Tab Screens
    object MainContainer : Screen("main_container")
    
    // Secondary Stack Screens
    object MarkAttendance : Screen("mark_attendance")
    object AttendanceSuccess : Screen("attendance_success")
    object AttendanceHistory : Screen("attendance_history")
    object MessMenu : Screen("mess_menu")
    
    // Settings Screens
    object AccountDetails : Screen("account_details")
    object Appearance : Screen("appearance")
    object Notifications : Screen("notifications")
    object HelpFeedback : Screen("help_feedback")
    object AboutVeda : Screen("about_veda")
}

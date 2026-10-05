package com.veda.vedahostelstudents.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.veda.vedahostelstudents.data.repository.HostelRepository
import com.veda.vedahostelstudents.data.repository.StudentAttendanceRepository
import com.veda.vedahostelstudents.ui.components.BottomTab
import com.veda.vedahostelstudents.ui.components.VedaBottomBar
import com.veda.vedahostelstudents.ui.screens.activation.AccountActivationScreen
import com.veda.vedahostelstudents.ui.screens.activation.ActivationSuccessScreen
import com.veda.vedahostelstudents.ui.screens.activation.SecureAccountScreen
import com.veda.vedahostelstudents.ui.screens.activity.ActivityScreen
import com.veda.vedahostelstudents.ui.screens.activity.AttendanceHistoryScreen
import com.veda.vedahostelstudents.ui.screens.attendance.AttendanceSuccessScreen
import com.veda.vedahostelstudents.ui.screens.attendance.MarkAttendanceScreen
import com.veda.vedahostelstudents.ui.screens.auth.ForgotPasswordScreen
import com.veda.vedahostelstudents.ui.screens.auth.LoginScreen
import com.veda.vedahostelstudents.ui.screens.hostel.HostelContactsScreen
import com.veda.vedahostelstudents.ui.screens.hostel.HostelNoticesScreen
import com.veda.vedahostelstudents.ui.screens.hostel.HostelScreen
import com.veda.vedahostelstudents.ui.screens.mess.MessMenuScreen
import com.veda.vedahostelstudents.ui.screens.onboarding.OnboardingScreen
import com.veda.vedahostelstudents.ui.screens.profile.AboutVedaScreen
import com.veda.vedahostelstudents.ui.screens.profile.AccountDetailsScreen
import androidx.compose.runtime.LaunchedEffect
import com.veda.vedahostelstudents.ui.screens.profile.AppearanceScreen
import com.veda.vedahostelstudents.ui.screens.profile.HelpFeedbackScreen
import com.veda.vedahostelstudents.ui.screens.profile.NotificationsScreen
import com.veda.vedahostelstudents.ui.screens.profile.ProfileScreen
import com.veda.vedahostelstudents.ui.screens.profile.ReportProblemScreen
import com.veda.vedahostelstudents.ui.screens.profile.SendFeedbackScreen
import com.veda.vedahostelstudents.ui.screens.splash.SplashScreen
import com.veda.vedahostelstudents.ui.screens.today.TodayScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(initialIntent: android.content.Intent? = null) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val student by HostelRepository.student.collectAsState()
    val activeSession by HostelRepository.activeSession.collectAsState()
    val nextSessionInfo by HostelRepository.nextSessionInfo.collectAsState()
    val attendanceSchedule by HostelRepository.attendanceSchedule.collectAsState()
    val hasMarkedCurrentSession by HostelRepository.hasMarkedCurrentSession.collectAsState()
    val currentSessionRecord by HostelRepository.currentSessionRecord.collectAsState()
    val attendanceHistory by HostelRepository.attendanceHistory.collectAsState()
    val isHistoryLoading by HostelRepository.isHistoryLoading.collectAsState()
    val historyError by HostelRepository.historyError.collectAsState()
    val notices by HostelRepository.notices.collectAsState()
    val contacts by HostelRepository.contacts.collectAsState()
    val messMenu by HostelRepository.messMenu.collectAsState()
    val hostelInfo by HostelRepository.hostelInfo.collectAsState()
    val isHostelLoading by HostelRepository.isHostelLoading.collectAsState()
    val hostelError by HostelRepository.hostelError.collectAsState()
    val attendanceReminders by HostelRepository.attendanceRemindersEnabled.collectAsState()
    val noticeAlerts by HostelRepository.noticeAlertsEnabled.collectAsState()
    val announcements by HostelRepository.announcementsEnabled.collectAsState()
    val appearancePref by HostelRepository.appearancePreference.collectAsState()

    var selectedBottomTab by remember { mutableStateOf(BottomTab.TODAY) }
    val unreadNoticesCount = notices.count { it.isUnread }

    val rawNotifType = initialIntent?.getStringExtra("notification_type") ?: initialIntent?.getStringExtra("type") ?: ""
    val notifType = when {
        rawNotifType.equals("ATTENDANCE", ignoreCase = true) || rawNotifType.equals("ATTENDANCE_REMINDER", ignoreCase = true) -> "ATTENDANCE_REMINDER"
        rawNotifType.equals("ANNOUNCEMENT", ignoreCase = true) || rawNotifType.equals("IMPORTANT_ANNOUNCEMENT", ignoreCase = true) -> "IMPORTANT_ANNOUNCEMENT"
        rawNotifType.equals("HOSTEL_NOTICE", ignoreCase = true) || rawNotifType.equals("NOTICE", ignoreCase = true) -> "HOSTEL_NOTICE"
        else -> rawNotifType
    }
    val noticeId = initialIntent?.getStringExtra("notice_id") ?: initialIntent?.getStringExtra("noticeId")

    LaunchedEffect(initialIntent) {
        if (notifType.isNotBlank() && student.isActivated) {
            if (!noticeId.isNullOrBlank()) {
                HostelRepository.markNoticeRead(noticeId)
            }
            if (notifType == "HOSTEL_NOTICE" || notifType == "IMPORTANT_ANNOUNCEMENT") {
                navController.navigate(Screen.MainContainer.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
                navController.navigate(Screen.HostelNotices.route)
            } else if (notifType == "ATTENDANCE_REMINDER") {
                selectedBottomTab = BottomTab.TODAY
                navController.navigate(Screen.MainContainer.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateNext = {
                    val auth = FirebaseAuth.getInstance()
                    val user = auth.currentUser
                    if (user != null) {
                        if (user.isAnonymous) {
                            navController.navigate(Screen.SecureAccount.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        } else if (student.isActivated) {
                            navController.navigate(Screen.MainContainer.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                            if (notifType == "HOSTEL_NOTICE" || notifType == "IMPORTANT_ANNOUNCEMENT") {
                                if (!noticeId.isNullOrBlank()) {
                                    HostelRepository.markNoticeRead(noticeId)
                                }
                                navController.navigate(Screen.HostelNotices.route)
                            }
                        } else {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    } else {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        // Onboarding Screen
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onGetStarted = {
                    navController.navigate(Screen.AccountActivation.route)
                },
                onSignIn = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        // Login Screen
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.MainContainer.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onActivateClick = {
                    navController.navigate(Screen.AccountActivation.route)
                }
            )
        }

        // Forgot Password Screen
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // Account Activation Screen
        composable(Screen.AccountActivation.route) {
            AccountActivationScreen(
                onBack = { navController.popBackStack() },
                onActivateSuccess = {
                    val auth = FirebaseAuth.getInstance()
                    val user = auth.currentUser
                    if (user != null && user.isAnonymous) {
                        navController.navigate(Screen.SecureAccount.route) {
                            popUpTo(Screen.AccountActivation.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.ActivationSuccess.route) {
                            popUpTo(Screen.AccountActivation.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        // Secure Account Screen (Mandatory Password Setup)
        composable(Screen.SecureAccount.route) {
            SecureAccountScreen(
                initialEmail = student.email,
                onComplete = {
                    navController.navigate(Screen.ActivationSuccess.route) {
                        popUpTo(Screen.SecureAccount.route) { inclusive = true }
                        popUpTo(Screen.AccountActivation.route) { inclusive = true }
                    }
                }
            )
        }

        // Activation Success Screen
        composable(Screen.ActivationSuccess.route) {
            ActivationSuccessScreen(
                student = student,
                onContinueToHome = {
                    navController.navigate(Screen.MainContainer.route) {
                        popUpTo(Screen.ActivationSuccess.route) { inclusive = true }
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // Main Dashboard Container with Bottom Navigation Bar (TODAY, ACTIVITY, HOSTEL, ME)
        composable(Screen.MainContainer.route) {
            Scaffold(
                bottomBar = {
                    VedaBottomBar(
                        currentTab = selectedBottomTab,
                        onTabSelected = { tab -> selectedBottomTab = tab }
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    when (selectedBottomTab) {
                        BottomTab.TODAY -> {
                            TodayScreen(
                                student = student,
                                activeSession = activeSession,
                                nextSessionInfo = nextSessionInfo,
                                schedule = attendanceSchedule,
                                hasMarkedCurrentSession = hasMarkedCurrentSession,
                                currentSessionRecord = currentSessionRecord,
                                unreadNoticesCount = unreadNoticesCount,
                                onNoticesClick = {
                                    navController.navigate(Screen.HostelNotices.route)
                                },
                                onMessMenuClick = {
                                    navController.navigate(Screen.MessMenu.route)
                                },
                                onContactsClick = {
                                    navController.navigate(Screen.HostelContacts.route)
                                },
                                onHostelInfoClick = {
                                    selectedBottomTab = BottomTab.HOSTEL
                                }
                            )
                        }

                        BottomTab.ACTIVITY -> {
                            ActivityScreen(
                                attendanceHistory = attendanceHistory,
                                isLoading = isHistoryLoading,
                                errorMessage = historyError,
                                onViewAllAttendance = {
                                    navController.navigate(Screen.AttendanceHistory.route)
                                }
                            )
                        }

                        BottomTab.HOSTEL -> {
                            HostelScreen(
                                student = student,
                                hostelInfo = hostelInfo,
                                isLoading = isHostelLoading,
                                errorMessage = hostelError,
                                onViewAllContactsClick = {
                                    navController.navigate(Screen.HostelContacts.route)
                                }
                            )
                        }

                        BottomTab.ME -> {
                            ProfileScreen(
                                student = student,
                                appearanceValue = appearancePref,
                                onAccountClick = { navController.navigate(Screen.AccountDetails.route) },
                                onAppearanceClick = { navController.navigate(Screen.Appearance.route) },
                                onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                                onHelpFeedbackClick = { navController.navigate(Screen.HelpFeedback.route) },
                                onAboutVedaClick = { navController.navigate(Screen.AboutVeda.route) },
                                onSignOutClick = {
                                    HostelRepository.signOut(context)
                                    selectedBottomTab = BottomTab.TODAY
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.MainContainer.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // Secondary Mark Attendance Details Screen
        composable(Screen.MarkAttendance.route) {
            val session = activeSession
            val scope = rememberCoroutineScope()

            if (session != null) {
                MarkAttendanceScreen(
                    session = session,
                    onBack = { navController.popBackStack() },
                    onMarkAttendance = { status ->
                        scope.launch {
                            val res = StudentAttendanceRepository.markAttendance(context, status)
                            res.onSuccess {
                                navController.navigate(Screen.AttendanceSuccess.route) {
                                    popUpTo(Screen.MarkAttendance.route) { inclusive = true }
                                }
                            }.onFailure { err ->
                                Toast.makeText(context, err.message ?: "Failed to mark attendance", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                )
            } else {
                navController.popBackStack()
            }
        }

        // Attendance Success Confirmation Screen
        composable(Screen.AttendanceSuccess.route) {
            val lastRecord = attendanceHistory.firstOrNull()
            AttendanceSuccessScreen(
                sessionTitle = lastRecord?.title ?: "Attendance",
                dateTimeText = lastRecord?.dateTimeText ?: "Today",
                onViewInActivity = {
                    selectedBottomTab = BottomTab.ACTIVITY
                    navController.navigate(Screen.MainContainer.route) {
                        popUpTo(Screen.AttendanceSuccess.route) { inclusive = true }
                    }
                },
                onBackToHome = {
                    selectedBottomTab = BottomTab.TODAY
                    navController.navigate(Screen.MainContainer.route) {
                        popUpTo(Screen.AttendanceSuccess.route) { inclusive = true }
                    }
                }
            )
        }

        // Attendance History View All Screen
        composable(Screen.AttendanceHistory.route) {
            AttendanceHistoryScreen(
                attendanceHistory = attendanceHistory,
                onBack = { navController.popBackStack() }
            )
        }

        // Mess Menu Screen
        composable(Screen.MessMenu.route) {
            MessMenuScreen(
                messMenu = messMenu,
                onBack = { navController.popBackStack() }
            )
        }

        // Hostel Notices Screen
        composable(Screen.HostelNotices.route) {
            HostelNoticesScreen(
                notices = notices,
                onNoticeClick = { notice ->
                    HostelRepository.markNoticeRead(notice.id)
                },
                onBack = { navController.popBackStack() }
            )
        }

        // Hostel Contacts Screen
        composable(Screen.HostelContacts.route) {
            HostelContactsScreen(
                contacts = contacts,
                onBack = { navController.popBackStack() }
            )
        }

        // Account Details Screen
        composable(Screen.AccountDetails.route) {
            AccountDetailsScreen(
                student = student,
                onBack = { navController.popBackStack() }
            )
        }

        // Appearance Screen
        composable(Screen.Appearance.route) {
            AppearanceScreen(
                currentPreference = appearancePref,
                onSelectPreference = { HostelRepository.setAppearancePreference(context, it) },
                onBack = { navController.popBackStack() }
            )
        }

        // Notifications Screen
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                attendanceReminders = attendanceReminders,
                noticeAlerts = noticeAlerts,
                announcements = announcements,
                onToggleAttendanceReminders = { HostelRepository.setAttendanceReminders(context, it) },
                onToggleNoticeAlerts = { HostelRepository.setNoticeAlerts(context, it) },
                onToggleAnnouncements = { HostelRepository.setAnnouncements(context, it) },
                onBack = { navController.popBackStack() }
            )
        }

        // Help & Feedback Screen
        composable(Screen.HelpFeedback.route) {
            HelpFeedbackScreen(
                wardenPhone = student.wardenPhone,
                onReportProblemClick = { navController.navigate(Screen.ReportProblem.route) },
                onSendFeedbackClick = { navController.navigate(Screen.SendFeedback.route) },
                onBack = { navController.popBackStack() }
            )
        }

        // Report a Problem Screen
        composable(Screen.ReportProblem.route) {
            ReportProblemScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // Send Feedback Screen
        composable(Screen.SendFeedback.route) {
            SendFeedbackScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // About VEDA Screen
        composable(Screen.AboutVeda.route) {
            AboutVedaScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

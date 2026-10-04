package com.veda.vedahostelstudents.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.veda.vedahostelstudents.data.repository.HostelRepository
import com.veda.vedahostelstudents.ui.components.BottomTab
import com.veda.vedahostelstudents.ui.components.VedaBottomBar
import com.veda.vedahostelstudents.ui.screens.activation.AccountActivationScreen
import com.veda.vedahostelstudents.ui.screens.activation.ActivationSuccessScreen
import com.veda.vedahostelstudents.ui.screens.activity.ActivityScreen
import com.veda.vedahostelstudents.ui.screens.activity.AttendanceHistoryScreen
import com.veda.vedahostelstudents.ui.screens.attendance.AttendanceSuccessScreen
import com.veda.vedahostelstudents.ui.screens.attendance.MarkAttendanceScreen
import com.veda.vedahostelstudents.ui.screens.hostel.HostelScreen
import com.veda.vedahostelstudents.ui.screens.mess.MessMenuScreen
import com.veda.vedahostelstudents.ui.screens.onboarding.OnboardingScreen
import com.veda.vedahostelstudents.ui.screens.profile.AboutVedaScreen
import com.veda.vedahostelstudents.ui.screens.profile.AccountDetailsScreen
import com.veda.vedahostelstudents.ui.screens.profile.AppearanceScreen
import com.veda.vedahostelstudents.ui.screens.profile.HelpFeedbackScreen
import com.veda.vedahostelstudents.ui.screens.profile.NotificationsScreen
import com.veda.vedahostelstudents.ui.screens.profile.ProfileScreen
import com.veda.vedahostelstudents.ui.screens.splash.SplashScreen
import com.veda.vedahostelstudents.ui.screens.today.TodayScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val student by HostelRepository.student.collectAsState()
    val activeSession by HostelRepository.activeSession.collectAsState()
    val hasMarkedCurrentSession by HostelRepository.hasMarkedCurrentSession.collectAsState()
    val attendanceHistory by HostelRepository.attendanceHistory.collectAsState()
    val notices by HostelRepository.notices.collectAsState()
    val messMenu by HostelRepository.messMenu.collectAsState()
    val hostelInfo by HostelRepository.hostelInfo.collectAsState()
    val attendanceReminders by HostelRepository.attendanceRemindersEnabled.collectAsState()
    val noticeAlerts by HostelRepository.noticeAlertsEnabled.collectAsState()
    val announcements by HostelRepository.announcementsEnabled.collectAsState()
    val appearancePref by HostelRepository.appearancePreference.collectAsState()

    var selectedBottomTab by remember { mutableStateOf(BottomTab.TODAY) }
    val unreadNoticesCount = notices.count { it.isUnread }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateNext = {
                    if (student.isActivated) {
                        navController.navigate(Screen.MainContainer.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
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
                }
            )
        }

        // Account Activation Screen
        composable(Screen.AccountActivation.route) {
            AccountActivationScreen(
                onBack = { navController.popBackStack() },
                onActivateSuccess = {
                    navController.navigate(Screen.ActivationSuccess.route) {
                        popUpTo(Screen.AccountActivation.route) { inclusive = true }
                    }
                },
                onVerifyCode = { code ->
                    HostelRepository.verifyActivationCode(context, code)
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
                Modifier.padding(innerPadding)

                when (selectedBottomTab) {
                    BottomTab.TODAY -> {
                        TodayScreen(
                            student = student,
                            activeSession = activeSession,
                            hasMarkedCurrentSession = hasMarkedCurrentSession,
                            unreadNoticesCount = unreadNoticesCount,
                            onMarkPresentClick = {
                                navController.navigate(Screen.MarkAttendance.route)
                            },
                            onNoticesClick = {
                                selectedBottomTab = BottomTab.HOSTEL
                            },
                            onMessMenuClick = {
                                navController.navigate(Screen.MessMenu.route)
                            },
                            onHostelInfoClick = {
                                selectedBottomTab = BottomTab.HOSTEL
                            }
                        )
                    }

                    BottomTab.ACTIVITY -> {
                        ActivityScreen(
                            attendanceHistory = attendanceHistory,
                            onViewAllAttendance = {
                                navController.navigate(Screen.AttendanceHistory.route)
                            }
                        )
                    }

                    BottomTab.HOSTEL -> {
                        HostelScreen(
                            student = student,
                            hostelInfo = hostelInfo,
                            notices = notices,
                            onNoticeClick = { notice ->
                                HostelRepository.markNoticeRead(notice.id)
                                Toast.makeText(context, "Notice: ${notice.title}", Toast.LENGTH_SHORT).show()
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
                                navController.navigate(Screen.Onboarding.route) {
                                    popUpTo(Screen.MainContainer.route) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }

        // Secondary Mark Attendance Details Screen
        composable(Screen.MarkAttendance.route) {
            val session = activeSession
            if (session != null) {
                MarkAttendanceScreen(
                    session = session,
                    onBack = { navController.popBackStack() },
                    onMarkPresence = {
                        HostelRepository.markAttendance()
                        navController.navigate(Screen.AttendanceSuccess.route) {
                            popUpTo(Screen.MarkAttendance.route) { inclusive = true }
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
                onSelectPreference = { HostelRepository.setAppearancePreference(it) },
                onBack = { navController.popBackStack() }
            )
        }

        // Notifications Screen
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                attendanceReminders = attendanceReminders,
                noticeAlerts = noticeAlerts,
                announcements = announcements,
                onToggleAttendanceReminders = { HostelRepository.setAttendanceReminders(it) },
                onToggleNoticeAlerts = { HostelRepository.setNoticeAlerts(it) },
                onToggleAnnouncements = { HostelRepository.setAnnouncements(it) },
                onBack = { navController.popBackStack() }
            )
        }

        // Help & Feedback Screen
        composable(Screen.HelpFeedback.route) {
            HelpFeedbackScreen(
                wardenPhone = student.wardenPhone,
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

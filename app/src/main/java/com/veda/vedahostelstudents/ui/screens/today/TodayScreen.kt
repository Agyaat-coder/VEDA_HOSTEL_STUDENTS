package com.veda.vedahostelstudents.ui.screens.today

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.AttendanceRecord
import com.veda.vedahostelstudents.data.model.AttendanceSession
import com.veda.vedahostelstudents.data.model.AttendanceSessionType
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.data.repository.AttendanceScheduleConfig
import com.veda.vedahostelstudents.data.repository.NextSessionInfo
import com.veda.vedahostelstudents.data.repository.StudentAttendanceRepository
import com.veda.vedahostelstudents.ui.components.QuickAccessCard
import com.veda.vedahostelstudents.ui.theme.VEDAHOSTELSTUDENTSTheme
import com.veda.vedahostelstudents.ui.theme.VedaAlertRed
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurfaceVariant
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreen
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun TodayScreen(
    student: Student,
    activeSession: AttendanceSession?,
    nextSessionInfo: NextSessionInfo?,
    schedule: AttendanceScheduleConfig,
    hasMarkedCurrentSession: Boolean,
    currentSessionRecord: AttendanceRecord?,
    unreadNoticesCount: Int,
    onNoticesClick: () -> Unit,
    onMessMenuClick: () -> Unit,
    onHostelInfoClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isMarkingAttendance by remember { mutableStateOf(false) }

    // Dynamic greeting calculation based on hour of day
    val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greetingPrefix = when (hourOfDay) {
        in 4..11 -> "Good morning,"
        in 12..16 -> "Good afternoon,"
        else -> "Good evening,"
    }

    val firstName = student.fullName.trim().split("\\s+".toRegex()).firstOrNull()?.takeIf { it.isNotBlank() }
        ?: student.name.trim().split("\\s+".toRegex()).firstOrNull()?.takeIf { it.isNotBlank() }

    val greetingText = if (firstName.isNullOrBlank()) {
        "Welcome 👋"
    } else {
        "$firstName 👋"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Top Bar: Dynamic Student Name & Room + Notification Bell
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = greetingPrefix,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = VedaTextMuted
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = greetingText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = VedaTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.CorporateFare,
                        contentDescription = "Room",
                        tint = VedaBrightBlue,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val roomHostelText = if (student.roomNumber.isNotBlank() && student.hostelName.isNotBlank()) {
                        "Room ${student.roomNumber}  •  ${student.hostelName}"
                    } else if (student.hostelName.isNotBlank()) {
                        student.hostelName
                    } else {
                        "Hostel Student"
                    }
                    Text(
                        text = roomHostelText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = VedaBrightBlue
                    )
                }
            }

            Box {
                IconButton(
                    onClick = onNoticesClick,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(VedaDarkSurface)
                ) {
                    Icon(
                        imageVector = Icons.Filled.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = VedaTextPrimary
                    )
                }
                if (unreadNoticesCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(VedaBrightBlue)
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }

        // Attendance Card Dynamic States
        when {
            // STATE 1: MISSING / UNCONFIGURED SCHEDULE
            !schedule.isConfigured -> {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = VedaDarkSurface
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(VedaTextMuted.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Schedule Not Set",
                                tint = VedaTextMuted,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Attendance Schedule Not Set",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = VedaTextPrimary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Attendance schedule is not configured yet.",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = VedaTextMuted
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Only your hostel warden can configure the schedule.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = VedaTextMuted.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // STATE 2: ACTIVE SESSION (UNMARKED)
            activeSession != null && !hasMarkedCurrentSession -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    VedaAlertRed.copy(alpha = 0.95f),
                                    VedaAlertRed.copy(alpha = 0.75f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val sessionIcon = if (activeSession.sessionType == AttendanceSessionType.MORNING) {
                                    Icons.Filled.WbSunny
                                } else {
                                    Icons.Filled.NightsStay
                                }

                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(VedaTextPrimary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = sessionIcon,
                                        contentDescription = activeSession.title,
                                        tint = VedaTextPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "${activeSession.title} is open",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VedaTextPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "${activeSession.startTimeText} – ${activeSession.endTimeText}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VedaTextPrimary.copy(alpha = 0.95f)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Please mark your presence before ${activeSession.cutoffTimeText}.",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = VedaTextPrimary.copy(alpha = 0.85f)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                if (isMarkingAttendance) return@Button
                                isMarkingAttendance = true
                                scope.launch {
                                    val result = StudentAttendanceRepository.markAttendance(context)
                                    isMarkingAttendance = false
                                    result.onSuccess {
                                        Toast.makeText(context, "Attendance Marked", Toast.LENGTH_SHORT).show()
                                    }.onFailure { err ->
                                        Toast.makeText(
                                            context,
                                            err.message ?: "Failed to mark attendance",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            },
                            enabled = !isMarkingAttendance,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = VedaTextPrimary,
                                contentColor = VedaAlertRed
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (isMarkingAttendance) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = VedaAlertRed,
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Marking attendance...",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                } else {
                                    Text(
                                        text = "Mark Present",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Mark Present",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // STATE 3: ATTENDANCE ALREADY MARKED
            activeSession != null && hasMarkedCurrentSession -> {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = VedaDarkSurface
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(VedaSuccessGreen.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Recorded",
                                tint = VedaSuccessGreen,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Attendance Marked",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = VedaTextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        val markSubtitle = if (!currentSessionRecord?.timeText.isNullOrBlank()) {
                            "Marked at ${currentSessionRecord.timeText}"
                        } else {
                            "Your presence has been recorded for this session."
                        }

                        Text(
                            text = markSubtitle,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = VedaTextMuted
                        )
                    }
                }
            }

            // STATE 4: NO ACTIVE SESSION
            else -> {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = VedaDarkSurface
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(VedaBrightBlue.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccessTime,
                                contentDescription = "No Active Session",
                                tint = VedaBrightBlue,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "No attendance session is active right now.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = VedaTextPrimary
                        )

                        if (nextSessionInfo != null) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(VedaDarkSurfaceVariant)
                                    .padding(14.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "NEXT SESSION",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VedaBrightBlue,
                                        letterSpacing = 1.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = nextSessionInfo.title,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VedaTextPrimary
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = nextSessionInfo.timeRangeText,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = VedaTextMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section Title: Quick Actions
        Text(
            text = "Quick Actions",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = VedaTextPrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Access Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickAccessCard(
                title = "Notices",
                badgeText = if (unreadNoticesCount > 0) "$unreadNoticesCount new" else "All read",
                icon = Icons.Filled.Notifications,
                onClick = onNoticesClick,
                modifier = Modifier.weight(1f)
            )

            QuickAccessCard(
                title = "Mess Menu",
                badgeText = "Today",
                icon = Icons.Filled.Restaurant,
                onClick = onMessMenuClick,
                modifier = Modifier.weight(1f)
            )

            QuickAccessCard(
                title = "Hostel Info",
                badgeText = "View",
                icon = Icons.Filled.CorporateFare,
                onClick = onHostelInfoClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayScreenPreview() {
    VEDAHOSTELSTUDENTSTheme {
        TodayScreen(
            student = Student(fullName = "Alex Smith", roomNumber = "101", hostelName = "Veda Hostel"),
            activeSession = AttendanceSession(
                sessionType = AttendanceSessionType.MORNING,
                title = "Morning Attendance",
                startTimeText = "07:00 AM",
                endTimeText = "09:00 AM",
                cutoffTimeText = "09:00 AM"
            ),
            nextSessionInfo = NextSessionInfo(
                sessionType = AttendanceSessionType.EVENING,
                title = "Evening Attendance",
                timeRangeText = "07:00 PM – 09:00 PM"
            ),
            schedule = AttendanceScheduleConfig(isConfigured = true),
            hasMarkedCurrentSession = false,
            currentSessionRecord = null,
            unreadNoticesCount = 2,
            onNoticesClick = {},
            onMessMenuClick = {},
            onHostelInfoClick = {}
        )
    }
}

package com.veda.vedahostelstudents.ui.screens.today

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.AttendanceSession
import com.veda.vedahostelstudents.data.model.AttendanceSessionType
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.ui.components.QuickAccessCard
import com.veda.vedahostelstudents.ui.theme.VEDAHOSTELSTUDENTSTheme
import com.veda.vedahostelstudents.ui.theme.VedaAlertRed
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreen
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun TodayScreen(
    student: Student,
    activeSession: AttendanceSession?,
    hasMarkedCurrentSession: Boolean,
    unreadNoticesCount: Int,
    onMarkPresentClick: () -> Unit,
    onNoticesClick: () -> Unit,
    onMessMenuClick: () -> Unit,
    onHostelInfoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Top Bar: Student Name & Room + Notification Bell
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Good morning,",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = VedaTextMuted
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${student.fullName.split(" ").firstOrNull() ?: student.fullName} 👋",
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
                    Text(
                        text = "Room ${student.roomNumber}  •  ${student.hostelName}",
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

        // Main Attendance Card Dynamic State
        if (activeSession != null) {
            if (!hasMarkedCurrentSession) {
                // ACTIVE ATTENDANCE OPEN CARD (MORNING OR EVENING)
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
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(VedaTextPrimary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Notifications,
                                        contentDescription = "Alert",
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

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Please mark your presence before ${activeSession.cutoffTimeText}.",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = VedaTextPrimary.copy(alpha = 0.9f)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = onMarkPresentClick,
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
            } else {
                // RECORDED FOR THIS SESSION CARD
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
                            text = "Attendance Recorded",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = VedaTextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Your presence has already been marked.",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = VedaTextMuted
                        )
                    }
                }
            }
        } else {
            // EMPTY / IDLE ATTENDANCE STATE CARD
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = VedaDarkSurface
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(VedaSuccessGreen.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Caught Up",
                            tint = VedaSuccessGreen,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "You're all caught up!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VedaTextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "No attendance is active right now.\nEnjoy your day!",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = VedaTextMuted,
                        lineHeight = 18.sp
                    )
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
            student = Student(),
            activeSession = AttendanceSession(
                sessionType = AttendanceSessionType.MORNING,
                title = "Morning Attendance",
                startTimeText = "07:00 AM",
                endTimeText = "09:00 AM",
                cutoffTimeText = "09:00 AM"
            ),
            hasMarkedCurrentSession = false,
            unreadNoticesCount = 2,
            onMarkPresentClick = {},
            onNoticesClick = {},
            onMessMenuClick = {},
            onHostelInfoClick = {}
        )
    }
}

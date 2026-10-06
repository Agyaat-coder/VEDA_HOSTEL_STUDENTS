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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.data.model.AttendanceRecord
import com.veda.vedahostelstudents.data.model.AttendanceSession
import com.veda.vedahostelstudents.data.model.AttendanceSessionType
import com.veda.vedahostelstudents.data.model.AttendanceStatus
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.data.repository.AttendanceScheduleConfig
import com.veda.vedahostelstudents.data.repository.NextSessionInfo
import com.veda.vedahostelstudents.data.repository.StudentAttendanceRepository
import com.veda.vedahostelstudents.ui.components.QuickAccessCard
import com.veda.vedahostelstudents.ui.components.VedaCard
import com.veda.vedahostelstudents.ui.components.VedaIconButton
import com.veda.vedahostelstudents.ui.components.VedaSectionHeader
import com.veda.vedahostelstudents.ui.components.VedaStatusBadge
import com.veda.vedahostelstudents.ui.components.VedaStatusStyle
import com.veda.vedahostelstudents.ui.theme.VEDAHOSTELSTUDENTSTheme
import com.veda.vedahostelstudents.ui.theme.VedaAbsent
import com.veda.vedahostelstudents.ui.theme.VedaAbsentSoft
import com.veda.vedahostelstudents.ui.theme.VedaBorder
import com.veda.vedahostelstudents.ui.theme.VedaCanvas
import com.veda.vedahostelstudents.ui.theme.VedaInk
import com.veda.vedahostelstudents.ui.theme.VedaMuted
import com.veda.vedahostelstudents.ui.theme.VedaPresent
import com.veda.vedahostelstudents.ui.theme.VedaPrimary
import com.veda.vedahostelstudents.ui.theme.VedaShapesInstance
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaSurface
import com.veda.vedahostelstudents.ui.theme.VedaTheme
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
    onContactsClick: () -> Unit = {},
    onHostelInfoClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isMarkingAttendance by remember { mutableStateOf(false) }
    var markingStatus by remember { mutableStateOf<AttendanceStatus?>(null) }
    var showConfirmAbsentDialog by remember { mutableStateOf(false) }

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

    val roomHostelText = if (student.roomNumber.isNotBlank() && student.hostelName.isNotBlank()) {
        "Room ${student.roomNumber} • ${student.hostelName}"
    } else if (student.hostelName.isNotBlank()) {
        student.hostelName
    } else {
        "Hostel Student"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaCanvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = VedaSpacingInstance.screenPaddingHorizontal)
    ) {
        Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

        // Top Greeting Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = VedaSpacingInstance.sm),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = greetingPrefix,
                    style = VedaTheme.typography.bodySecondary,
                    color = VedaMuted
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = greetingText,
                    style = VedaTheme.typography.screenTitle,
                    color = VedaInk
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.CorporateFare,
                        contentDescription = "Room",
                        tint = VedaPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = roomHostelText,
                        style = VedaTheme.typography.caption,
                        color = VedaPrimary
                    )
                }
            }

            Box {
                VedaIconButton(
                    icon = Icons.Filled.NotificationsNone,
                    contentDescription = "Notifications",
                    onClick = onNoticesClick,
                    tint = VedaInk,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(VedaSurface)
                )
                if (unreadNoticesCount > 0) {
                    Box(
                        modifier = Modifier
                            .padding(end = 4.dp, top = 4.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(VedaPrimary)
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.lg))

        // VEDA ATTENDANCE PULSE CARD (5 CANONICAL STATES - PAGES 42-45)
        VedaCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = VedaSurface,
            borderColor = VedaBorder,
            shape = VedaShapesInstance.card,
            contentPadding = VedaSpacingInstance.cardPadding
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val sessionTitle = (activeSession?.title ?: nextSessionInfo?.title ?: "EVENING ATTENDANCE").uppercase()
                val sessionTimes = activeSession?.let { "${it.startTimeText} - ${it.endTimeText}" }
                    ?: nextSessionInfo?.timeRangeText
                    ?: "05:55 PM - 06:00 PM"

                // Overline Session Title & Time Range
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sessionTitle,
                        style = VedaTheme.typography.caption,
                        color = VedaPrimary
                    )
                    Text(
                        text = sessionTimes,
                        style = VedaTheme.typography.caption,
                        color = VedaMuted
                    )
                }

                Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))

                when {
                    // STATE 1: ACTIVE SESSION - NOT MARKED
                    activeSession != null && !hasMarkedCurrentSession -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            VedaStatusBadge(
                                text = "NOT MARKED",
                                style = VedaStatusStyle.NOT_MARKED
                            )
                        }

                        Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))

                        Text(
                            text = "Attendance not marked",
                            style = VedaTheme.typography.sectionTitle,
                            color = VedaInk
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "You haven't marked your attendance yet.",
                            style = VedaTheme.typography.bodySecondary,
                            color = VedaMuted
                        )

                        Spacer(modifier = Modifier.height(VedaSpacingInstance.lg))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(VedaSpacingInstance.sm)
                        ) {
                            Button(
                                onClick = {
                                    if (isMarkingAttendance) return@Button
                                    isMarkingAttendance = true
                                    markingStatus = AttendanceStatus.PRESENT
                                    scope.launch {
                                        val result = StudentAttendanceRepository.markAttendance(context, AttendanceStatus.PRESENT)
                                        isMarkingAttendance = false
                                        result.onSuccess {
                                            Toast.makeText(context, "Attendance Marked: PRESENT", Toast.LENGTH_SHORT).show()
                                        }.onFailure { err ->
                                            Toast.makeText(context, err.message ?: "Failed to mark attendance", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                },
                                enabled = !isMarkingAttendance,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = VedaShapesInstance.button,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = VedaPresent,
                                    contentColor = VedaSurface
                                )
                            ) {
                                if (isMarkingAttendance && markingStatus == AttendanceStatus.PRESENT) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(18.dp),
                                        color = VedaSurface,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "MARK PRESENT",
                                            style = VedaTheme.typography.caption
                                        )
                                    }
                                }
                            }

                            Button(
                                onClick = { showConfirmAbsentDialog = true },
                                enabled = !isMarkingAttendance,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = VedaShapesInstance.button,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = VedaAbsentSoft,
                                    contentColor = VedaAbsent
                                )
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Cancel,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "MARK ABSENT",
                                        style = VedaTheme.typography.caption
                                    )
                                }
                            }
                        }
                    }

                    // STATE 2 & 3: ATTENDANCE MARKED (PRESENT / ABSENT)
                    activeSession != null && hasMarkedCurrentSession -> {
                        val isPresent = currentSessionRecord?.status == AttendanceStatus.PRESENT
                        val badgeText = if (isPresent) "✔ PRESENT" else "✖ ABSENT"
                        val badgeStyle = if (isPresent) VedaStatusStyle.PRESENT else VedaStatusStyle.ABSENT
                        val timeRecordedText = currentSessionRecord?.timeText?.ifBlank { "05:58 PM" } ?: "05:58 PM"
                        val dateRecordedText = currentSessionRecord?.dateText?.ifBlank { "05 Oct 2026" } ?: "05 Oct 2026"

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            VedaStatusBadge(
                                text = badgeText,
                                style = badgeStyle
                            )
                        }

                        Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))

                        Text(
                            text = "Attendance Marked",
                            style = VedaTheme.typography.sectionTitle,
                            color = VedaInk
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Recorded at $timeRecordedText • $dateRecordedText",
                            style = VedaTheme.typography.bodySecondary,
                            color = VedaMuted
                        )

                        Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))

                        Text(
                            text = "Saved securely. Attendance cannot be edited.",
                            style = VedaTheme.typography.caption,
                            color = VedaMuted
                        )
                    }

                    // STATE 4 & 5: CLOSED OR UPCOMING
                    else -> {
                        val isClosed = nextSessionInfo == null
                        val badgeText = if (isClosed) "CLOSED" else "UPCOMING"
                        val titleText = if (isClosed) "Attendance session closed" else "Next attendance starts soon"
                        val subtitleText = if (isClosed) "This session ended." else "Your next check-in will open soon."

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            VedaStatusBadge(
                                text = badgeText,
                                style = VedaStatusStyle.NOT_MARKED
                            )
                        }

                        Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))

                        Text(
                            text = titleText,
                            style = VedaTheme.typography.sectionTitle,
                            color = VedaInk
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = subtitleText,
                            style = VedaTheme.typography.bodySecondary,
                            color = VedaMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // Section Title: Quick Access
        VedaSectionHeader(
            title = "Quick Access"
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        // Quick Access Grid Items
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(VedaSpacingInstance.sm)
        ) {
            QuickAccessCard(
                title = "Notices",
                badgeText = if (unreadNoticesCount > 0) "$unreadNoticesCount new" else "View",
                icon = Icons.Filled.Notifications,
                onClick = onNoticesClick,
                modifier = Modifier.weight(1f)
            )

            QuickAccessCard(
                title = "Mess",
                badgeText = "Today",
                icon = Icons.Filled.Restaurant,
                onClick = onMessMenuClick,
                modifier = Modifier.weight(1f)
            )

            QuickAccessCard(
                title = "Contacts",
                badgeText = "Call",
                icon = Icons.Filled.Contacts,
                onClick = onContactsClick,
                modifier = Modifier.weight(1f)
            )

            QuickAccessCard(
                title = "Hostel",
                badgeText = "View",
                icon = Icons.Filled.CorporateFare,
                onClick = onHostelInfoClick,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.lg))

        // Supporting Line: "A little more connected to campus."
        Text(
            text = "A little more connected to campus.",
            style = VedaTheme.typography.bodySecondary,
            color = VedaMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xxl))
    }

    // Confirm Absent Dialog (Page 37)
    if (showConfirmAbsentDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmAbsentDialog = false },
            title = {
                Text(
                    text = "Mark yourself absent?",
                    style = VedaTheme.typography.sectionTitle,
                    color = VedaInk
                )
            },
            text = {
                Text(
                    text = "This records ABSENT for this session. You cannot edit it afterward.",
                    style = VedaTheme.typography.bodySecondary,
                    color = VedaMuted
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmAbsentDialog = false
                        isMarkingAttendance = true
                        markingStatus = AttendanceStatus.ABSENT
                        scope.launch {
                            val result = StudentAttendanceRepository.markAttendance(context, AttendanceStatus.ABSENT)
                            isMarkingAttendance = false
                            result.onSuccess {
                                Toast.makeText(context, "Attendance Marked: ABSENT", Toast.LENGTH_SHORT).show()
                            }.onFailure { err ->
                                Toast.makeText(context, err.message ?: "Failed to mark attendance", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VedaAbsent,
                        contentColor = VedaSurface
                    ),
                    shape = VedaShapesInstance.button
                ) {
                    Text("Mark Absent", style = VedaTheme.typography.caption)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmAbsentDialog = false }) {
                    Text("Cancel", style = VedaTheme.typography.caption, color = VedaPrimary)
                }
            },
            containerColor = VedaSurface,
            shape = VedaShapesInstance.dialog
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodayScreenPreview() {
    VEDAHOSTELSTUDENTSTheme {
        TodayScreen(
            student = Student(fullName = "Vivek Anand", roomNumber = "301", hostelName = "Charak Chatravas"),
            activeSession = AttendanceSession(
                sessionType = AttendanceSessionType.EVENING,
                title = "EVENING ATTENDANCE",
                startTimeText = "05:55 PM",
                endTimeText = "06:00 PM",
                cutoffTimeText = "06:00 PM"
            ),
            nextSessionInfo = NextSessionInfo(
                sessionType = AttendanceSessionType.EVENING,
                title = "EVENING ATTENDANCE",
                timeRangeText = "05:55 PM - 06:00 PM"
            ),
            schedule = AttendanceScheduleConfig(isConfigured = true),
            hasMarkedCurrentSession = false,
            currentSessionRecord = null,
            unreadNoticesCount = 2,
            onNoticesClick = {},
            onMessMenuClick = {},
            onContactsClick = {},
            onHostelInfoClick = {}
        )
    }
}

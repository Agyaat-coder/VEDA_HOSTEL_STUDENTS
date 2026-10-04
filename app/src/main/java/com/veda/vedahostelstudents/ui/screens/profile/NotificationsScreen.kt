package com.veda.vedahostelstudents.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurfaceVariant
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun NotificationsScreen(
    attendanceReminders: Boolean,
    noticeAlerts: Boolean,
    announcements: Boolean,
    onToggleAttendanceReminders: (Boolean) -> Unit,
    onToggleNoticeAlerts: (Boolean) -> Unit,
    onToggleAnnouncements: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .padding(20.dp)
    ) {
        // Top Nav Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = VedaTextPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Notifications",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VedaTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = VedaDarkSurface
        ) {
            Column {
                NotificationToggleRow(
                    title = "Attendance Reminders",
                    subtitle = "Get notified when warden opens an active session",
                    checked = attendanceReminders,
                    onCheckedChange = onToggleAttendanceReminders
                )
                NotificationToggleRow(
                    title = "Hostel Notices",
                    subtitle = "Receive alerts for new hostel maintenance & news",
                    checked = noticeAlerts,
                    onCheckedChange = onToggleNoticeAlerts
                )
                NotificationToggleRow(
                    title = "Important Announcements",
                    subtitle = "Urgent broadcasts from Chief Warden",
                    checked = announcements,
                    onCheckedChange = onToggleAnnouncements,
                    showDivider = false
                )
            }
        }
    }
}

@Composable
private fun NotificationToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    showDivider: Boolean = true
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VedaTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = VedaTextMuted
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = VedaTextPrimary,
                    checkedTrackColor = VedaBrightBlue,
                    uncheckedThumbColor = VedaTextMuted,
                    uncheckedTrackColor = VedaDarkSurfaceVariant
                )
            )
        }

        if (showDivider) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(VedaDarkBackground)
            )
        }
    }
}

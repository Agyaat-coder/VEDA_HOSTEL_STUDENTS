package com.veda.vedahostelstudents.ui.screens.profile

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.ui.components.VedaCard
import com.veda.vedahostelstudents.ui.components.VedaIconButton
import com.veda.vedahostelstudents.ui.components.VedaStatusBadge
import com.veda.vedahostelstudents.ui.components.VedaStatusStyle
import com.veda.vedahostelstudents.ui.theme.VedaAccentSoft
import com.veda.vedahostelstudents.ui.theme.VedaBorder
import com.veda.vedahostelstudents.ui.theme.VedaCanvas
import com.veda.vedahostelstudents.ui.theme.VedaDivider
import com.veda.vedahostelstudents.ui.theme.VedaInk
import com.veda.vedahostelstudents.ui.theme.VedaMuted
import com.veda.vedahostelstudents.ui.theme.VedaPrimary
import com.veda.vedahostelstudents.ui.theme.VedaShapesInstance
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaSurface
import com.veda.vedahostelstudents.ui.theme.VedaTheme

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
            .background(VedaCanvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = VedaSpacingInstance.screenPaddingHorizontal)
    ) {
        Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

        // Top Nav Bar Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = VedaSpacingInstance.sm)
        ) {
            VedaIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                onClick = onBack,
                tint = VedaInk
            )
            Spacer(modifier = Modifier.width(VedaSpacingInstance.xs))
            Text(
                text = "Notifications",
                style = VedaTheme.typography.screenTitle,
                color = VedaInk
            )
            Spacer(modifier = Modifier.width(VedaSpacingInstance.sm))
            VedaStatusBadge(
                text = "Coming Soon",
                style = VedaStatusStyle.WARNING
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        Text(
            text = "Stay in the loop, without the noise.",
            style = VedaTheme.typography.sectionTitle,
            color = VedaInk
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

        Text(
            text = "Push notifications will be available in a future update.",
            style = VedaTheme.typography.bodySecondary,
            color = VedaMuted
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // Notification Setting Toggles Card
        VedaCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = VedaSurface,
            borderColor = VedaBorder,
            shape = VedaShapesInstance.large,
            contentPadding = 0.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                NotificationToggleRow(
                    title = "Attendance Reminders",
                    subtitle = "A gentle nudge before each session.",
                    checked = attendanceReminders,
                    onCheckedChange = onToggleAttendanceReminders
                )
                NotificationToggleRow(
                    title = "Hostel Notices",
                    subtitle = "Updates from your warden office.",
                    checked = noticeAlerts,
                    onCheckedChange = onToggleNoticeAlerts
                )
                NotificationToggleRow(
                    title = "Important Announcements",
                    subtitle = "Priority campus and safety updates.",
                    checked = announcements,
                    onCheckedChange = onToggleAnnouncements,
                    showDivider = false
                )
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // ANDROID NOTIFICATION PREVIEW SECTION (PAGES 24 & 31)
        Text(
            text = "ANDROID NOTIFICATION PREVIEW",
            style = VedaTheme.typography.caption,
            color = VedaMuted
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.sm)
        ) {
            // Preview Card 1
            VedaCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = VedaSurface,
                borderColor = VedaBorder,
                shape = VedaShapesInstance.medium,
                contentPadding = VedaSpacingInstance.cardPadding
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(VedaAccentSoft),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notification",
                            tint = VedaPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(VedaSpacingInstance.md))
                    Column {
                        Text(
                            text = "VEDA HOSTEL • now",
                            style = VedaTheme.typography.caption,
                            color = VedaPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Time for evening attendance",
                            style = VedaTheme.typography.body,
                            fontWeight = FontWeight.Bold,
                            color = VedaInk
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Your check-in opens at 05:55 PM.",
                            style = VedaTheme.typography.caption,
                            color = VedaMuted
                        )
                    }
                }
            }

            // Preview Card 2
            VedaCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = VedaSurface,
                borderColor = VedaBorder,
                shape = VedaShapesInstance.medium,
                contentPadding = VedaSpacingInstance.cardPadding
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(VedaAccentSoft),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notification",
                            tint = VedaPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(VedaSpacingInstance.md))
                    Column {
                        Text(
                            text = "VEDA HOSTEL • now",
                            style = VedaTheme.typography.caption,
                            color = VedaPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "New hostel notice",
                            style = VedaTheme.typography.body,
                            fontWeight = FontWeight.Bold,
                            color = VedaInk
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Diwali Holiday • Tap to read the details.",
                            style = VedaTheme.typography.caption,
                            color = VedaMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        // Footnote
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = VedaSpacingInstance.xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = VedaMuted,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Android notification permissions must be enabled in your device settings.",
                style = VedaTheme.typography.caption,
                color = VedaMuted
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xxl))
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
                .padding(horizontal = VedaSpacingInstance.cardPadding, vertical = VedaSpacingInstance.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = VedaTheme.typography.body,
                    fontWeight = FontWeight.Bold,
                    color = VedaInk
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = VedaTheme.typography.caption,
                    color = VedaMuted
                )
            }

            Spacer(modifier = Modifier.width(VedaSpacingInstance.sm))

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = VedaSurface,
                    checkedTrackColor = VedaPrimary,
                    uncheckedThumbColor = VedaMuted,
                    uncheckedTrackColor = VedaAccentSoft
                )
            )
        }

        if (showDivider) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(VedaDivider)
            )
        }
    }
}

package com.veda.vedahostelstudents.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.ui.theme.VedaAlertRed
import com.veda.vedahostelstudents.ui.theme.VedaAlertRedBg
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun ProfileScreen(
    student: Student,
    appearanceValue: String,
    onAccountClick: () -> Unit,
    onAppearanceClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onHelpFeedbackClick: () -> Unit,
    onAboutVedaClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header Title
        Text(
            text = "Me",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = VedaTextPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 20.dp)
        )

        // Profile Avatar Header
        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(VedaBrightBlue.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile Avatar",
                tint = VedaBrightBlue,
                modifier = Modifier.size(52.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = student.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = VedaTextPrimary
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Student ID: ${student.id}",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = VedaBrightBlue
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${student.hostelName}  •  Room ${student.roomNumber}",
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = VedaTextMuted
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Menu Card Items
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = VedaDarkSurface
        ) {
            Column {
                MenuItem(
                    icon = Icons.Filled.Person,
                    title = "Account",
                    value = null,
                    onClick = onAccountClick
                )
                MenuItem(
                    icon = Icons.Filled.Palette,
                    title = "Appearance",
                    value = appearanceValue,
                    onClick = onAppearanceClick
                )
                MenuItem(
                    icon = Icons.Filled.Notifications,
                    title = "Notifications",
                    value = null,
                    onClick = onNotificationsClick
                )
                MenuItem(
                    icon = Icons.AutoMirrored.Filled.Help,
                    title = "Help & Feedback",
                    value = null,
                    onClick = onHelpFeedbackClick
                )
                MenuItem(
                    icon = Icons.Filled.Info,
                    title = "About VEDA",
                    value = "Version 1.0.0",
                    onClick = onAboutVedaClick,
                    showDivider = false
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Sign Out Button (Destructive Red)
        Button(
            onClick = onSignOutClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VedaAlertRedBg,
                contentColor = VedaAlertRed
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Sign Out",
                    tint = VedaAlertRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign Out",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun MenuItem(
    icon: ImageVector,
    title: String,
    value: String?,
    onClick: () -> Unit,
    showDivider: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(VedaBrightBlue.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = VedaBrightBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VedaTextPrimary
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (value != null) {
                    Text(
                        text = value,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = VedaTextMuted
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "Open",
                    tint = VedaTextMuted
                )
            }
        }

        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .height(1.dp)
                    .background(VedaDarkBackground)
            )
        }
    }
}

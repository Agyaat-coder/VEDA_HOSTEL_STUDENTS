package com.veda.vedahostelstudents.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreen
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreenBg
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
    var showSignOutDialog by remember { mutableStateOf(false) }

    // Initials calculation for avatar
    val displayName = student.fullName.ifBlank { student.name }
    val initials = displayName.trim().split("\\s+".toRegex())
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")
        .uppercase()

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
            if (initials.isNotBlank()) {
                Text(
                    text = initials,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = VedaBrightBlue
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile Avatar",
                    tint = VedaBrightBlue,
                    modifier = Modifier.size(52.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = displayName.ifBlank { "Student Profile" },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = VedaTextPrimary
        )

        if (student.rollNumber.isNotBlank()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Roll No: ${student.rollNumber}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = VedaBrightBlue
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Account Status Chip
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(VedaSuccessGreenBg)
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = "● Account Active",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = VedaSuccessGreen
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section 1: Personal Information
        ProfileSectionCard(title = "Personal Information", icon = Icons.Filled.Person) {
            InfoRowItem(label = "Full Name", value = displayName.ifBlank { "Not available" })
            InfoRowItem(label = "Email", value = student.email.ifBlank { "Not available" })
            InfoRowItem(label = "Phone", value = student.phoneNumber.ifBlank { "Not available" })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section 2: Academic Information
        ProfileSectionCard(title = "Academic Information", icon = Icons.Filled.School) {
            InfoRowItem(label = "Course", value = student.course.ifBlank { "Not available" })
            InfoRowItem(label = "Branch", value = student.branch.ifBlank { "Not available" })
            InfoRowItem(label = "Year", value = student.year.ifBlank { "Not available" })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section 3: Hostel Information
        ProfileSectionCard(title = "Hostel Information", icon = Icons.Filled.CorporateFare) {
            InfoRowItem(label = "Hostel", value = student.hostelName.ifBlank { "Not available" })
            InfoRowItem(label = "Room Number", value = if (student.roomNumber.isNotBlank()) "Room ${student.roomNumber}" else "Not available")
            InfoRowItem(label = "Floor", value = if (student.floorNumber.isNotBlank()) "Floor ${student.floorNumber}" else "Not available")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section 4: App & Account Settings Menu Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = VedaDarkSurface
        ) {
            Column {
                MenuItem(
                    icon = Icons.Filled.Person,
                    title = "Account Details",
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

        Spacer(modifier = Modifier.height(16.dp))

        // Read-only notice text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Read-Only",
                tint = VedaTextMuted,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Administrative student details are read-only and maintained by Warden administration.",
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = VedaTextMuted,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Log Out Button (Destructive Red)
        Button(
            onClick = { showSignOutDialog = true },
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
                    contentDescription = "Log Out",
                    tint = VedaAlertRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Log Out",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Logout Confirmation Dialog
    if (showSignOutDialog) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog = false },
            title = {
                Text(
                    text = "Log out?",
                    color = VedaTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to log out of this account?",
                    color = VedaTextMuted,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSignOutDialog = false
                        onSignOutClick()
                    }
                ) {
                    Text("Log Out", color = VedaAlertRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showSignOutDialog = false }
                ) {
                    Text("Cancel", color = VedaBrightBlue, fontWeight = FontWeight.SemiBold)
                }
            },
            containerColor = VedaDarkSurface,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
private fun ProfileSectionCard(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = VedaDarkSurface
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(VedaBrightBlue.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = VedaBrightBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = VedaTextPrimary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = content
            )
        }
    }
}

@Composable
private fun InfoRowItem(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = VedaTextMuted
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = VedaTextPrimary
        )
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

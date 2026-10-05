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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.ui.components.VedaCard
import com.veda.vedahostelstudents.ui.components.VedaStatusBadge
import com.veda.vedahostelstudents.ui.components.VedaStatusStyle
import com.veda.vedahostelstudents.ui.theme.VedaAccentSoft
import com.veda.vedahostelstudents.ui.theme.VedaBorder
import com.veda.vedahostelstudents.ui.theme.VedaCanvas
import com.veda.vedahostelstudents.ui.theme.VedaDivider
import com.veda.vedahostelstudents.ui.theme.VedaError
import com.veda.vedahostelstudents.ui.theme.VedaErrorSoft
import com.veda.vedahostelstudents.ui.theme.VedaInk
import com.veda.vedahostelstudents.ui.theme.VedaMuted
import com.veda.vedahostelstudents.ui.theme.VedaPrimary
import com.veda.vedahostelstudents.ui.theme.VedaShapesInstance
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaSurface
import com.veda.vedahostelstudents.ui.theme.VedaTheme

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

    val roomHostelText = if (student.roomNumber.isNotBlank() && student.hostelName.isNotBlank()) {
        "Room ${student.roomNumber} / ${student.hostelName}"
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
            .padding(horizontal = VedaSpacingInstance.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

        // Top Header Title
        Text(
            text = "Me",
            style = VedaTheme.typography.screenTitle,
            color = VedaInk,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = VedaSpacingInstance.md)
        )

        // PROMINENT STUDENT IDENTITY CARD (Page 25 & 30)
        VedaCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = VedaSurface,
            borderColor = VedaBorder,
            shape = VedaShapesInstance.card,
            contentPadding = VedaSpacingInstance.cardPadding
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(VedaAccentSoft),
                        contentAlignment = Alignment.Center
                    ) {
                        if (initials.isNotBlank()) {
                            Text(
                                text = initials,
                                style = VedaTheme.typography.sectionTitle,
                                fontWeight = FontWeight.Bold,
                                color = VedaPrimary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Avatar",
                                tint = VedaPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(VedaSpacingInstance.md))

                    Column {
                        Text(
                            text = displayName.ifBlank { "Student Profile" },
                            style = VedaTheme.typography.sectionTitle,
                            color = VedaInk
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = roomHostelText,
                            style = VedaTheme.typography.bodySecondary,
                            color = VedaMuted
                        )
                    }
                }

                VedaStatusBadge(
                    text = "ACTIVE",
                    style = VedaStatusStyle.SUCCESS
                )
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // GROUPED NAVIGATION SECTIONS (PAGES 25 & 30)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.lg)
        ) {
            // ACCOUNT SECTION
            MenuSection(title = "ACCOUNT") {
                MenuItem(
                    icon = Icons.Filled.Person,
                    title = "Account Details",
                    value = null,
                    onClick = onAccountClick,
                    showDivider = false
                )
            }

            // PREFERENCES SECTION
            MenuSection(title = "PREFERENCES") {
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
                    onClick = onNotificationsClick,
                    showDivider = false
                )
            }

            // SUPPORT SECTION
            MenuSection(title = "SUPPORT") {
                MenuItem(
                    icon = Icons.AutoMirrored.Filled.Help,
                    title = "Help & Feedback",
                    value = null,
                    onClick = onHelpFeedbackClick,
                    showDivider = false
                )
            }

            // ABOUT SECTION
            MenuSection(title = "ABOUT") {
                MenuItem(
                    icon = Icons.Filled.Info,
                    title = "About VEDA",
                    value = "Version 1.0.0",
                    onClick = onAboutVedaClick,
                    showDivider = false
                )
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // Log Out Button (Destructive Red)
        Button(
            onClick = { showSignOutDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = VedaShapesInstance.button,
            colors = ButtonDefaults.buttonColors(
                containerColor = VedaErrorSoft,
                contentColor = VedaError
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Log Out",
                    tint = VedaError,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(VedaSpacingInstance.sm))
                Text(
                    text = "LOG OUT",
                    style = VedaTheme.typography.button,
                    color = VedaError
                )
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        // Product Tagline & Version Text
        Text(
            text = "VEDA CAMPUS PULSE • Version 1.0.0",
            style = VedaTheme.typography.caption,
            color = VedaMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xxl))
    }

    // Logout Confirmation Dialog
    if (showSignOutDialog) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog = false },
            title = {
                Text(
                    text = "Log out?",
                    style = VedaTheme.typography.sectionTitle,
                    color = VedaInk
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to log out of this account?",
                    style = VedaTheme.typography.bodySecondary,
                    color = VedaMuted
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSignOutDialog = false
                        onSignOutClick()
                    }
                ) {
                    Text(
                        text = "Log Out",
                        style = VedaTheme.typography.label,
                        fontWeight = FontWeight.Bold,
                        color = VedaError
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showSignOutDialog = false }
                ) {
                    Text(
                        text = "Cancel",
                        style = VedaTheme.typography.label,
                        fontWeight = FontWeight.SemiBold,
                        color = VedaPrimary
                    )
                }
            },
            containerColor = VedaSurface,
            shape = VedaShapesInstance.dialog
        )
    }
}

@Composable
private fun MenuSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = VedaTheme.typography.caption,
            color = VedaMuted,
            modifier = Modifier.padding(start = 4.dp, bottom = VedaSpacingInstance.xs)
        )

        VedaCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = VedaSurface,
            borderColor = VedaBorder,
            shape = VedaShapesInstance.large,
            contentPadding = 0.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                content()
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
                .padding(horizontal = VedaSpacingInstance.cardPadding, vertical = VedaSpacingInstance.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(VedaShapesInstance.small)
                        .background(VedaAccentSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = VedaPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(VedaSpacingInstance.md))
                Text(
                    text = title,
                    style = VedaTheme.typography.body,
                    fontWeight = FontWeight.SemiBold,
                    color = VedaInk
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (value != null) {
                    Text(
                        text = value,
                        style = VedaTheme.typography.caption,
                        color = VedaMuted
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "Open",
                    tint = VedaMuted
                )
            }
        }

        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = VedaSpacingInstance.cardPadding)
                    .height(1.dp)
                    .background(VedaDivider)
            )
        }
    }
}

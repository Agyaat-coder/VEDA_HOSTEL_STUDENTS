package com.veda.vedahostelstudents.ui.screens.hostel

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Stairs
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.data.model.ContactInfo
import com.veda.vedahostelstudents.data.model.HostelInfo
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.ui.components.VedaCard
import com.veda.vedahostelstudents.ui.components.VedaEmptyState
import com.veda.vedahostelstudents.ui.components.VedaErrorState
import com.veda.vedahostelstudents.ui.components.VedaIconButton
import com.veda.vedahostelstudents.ui.components.VedaLoadingState
import com.veda.vedahostelstudents.ui.components.VedaSectionHeader
import com.veda.vedahostelstudents.ui.theme.VedaAccentSoft
import com.veda.vedahostelstudents.ui.theme.VedaBorder
import com.veda.vedahostelstudents.ui.theme.VedaCanvas
import com.veda.vedahostelstudents.ui.theme.VedaInk
import com.veda.vedahostelstudents.ui.theme.VedaMuted
import com.veda.vedahostelstudents.ui.theme.VedaPrimary
import com.veda.vedahostelstudents.ui.theme.VedaShapesInstance
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaSuccess
import com.veda.vedahostelstudents.ui.theme.VedaSuccessSoft
import com.veda.vedahostelstudents.ui.theme.VedaSurface
import com.veda.vedahostelstudents.ui.theme.VedaTheme

@Composable
fun HostelScreen(
    student: Student,
    hostelInfo: HostelInfo,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onViewAllContactsClick: () -> Unit = {}
) {
    val context = LocalContext.current

    val hostelNameText = hostelInfo.name.ifBlank { student.hostelName.ifBlank { "Hostel" } }
    val locationText = hostelInfo.campus.ifBlank { hostelInfo.city }
    val formattedAddress = formatAddress(hostelInfo)
    val wardenNameText = hostelInfo.wardenName.ifBlank { student.wardenName }
    val previewContacts = hostelInfo.contacts
        .filter { it.isActive }
        .sortedBy { it.priority }
        .take(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaCanvas)
            .padding(horizontal = VedaSpacingInstance.screenPaddingHorizontal)
    ) {
        Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

        // Screen Header Title
        Text(
            text = "Hostel",
            style = VedaTheme.typography.screenTitle,
            fontWeight = FontWeight.Bold,
            color = VedaInk,
            modifier = Modifier.padding(vertical = VedaSpacingInstance.md)
        )

        when {
            // STATE 1: LOADING
            isLoading && hostelInfo.name.isBlank() && student.hostelName.isBlank() -> {
                VedaLoadingState(message = "Loading hostel information...")
            }

            // STATE 2: ERROR
            errorMessage != null && hostelInfo.name.isBlank() -> {
                VedaErrorState(
                    title = "Unable to load hostel information",
                    message = errorMessage
                )
            }

            // STATE 3: HOSTEL INFORMATION CONTENT
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.listSpacing)
                ) {
                    // 1. HOSTEL IDENTITY CARD WITH BUILDING ILLUSTRATION
                    item {
                        VedaCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = VedaSurface,
                            borderColor = VedaBorder,
                            shape = VedaShapesInstance.card,
                            contentPadding = VedaSpacingInstance.cardPadding
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                // Decorative Building Illustration Header
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                        .clip(VedaShapesInstance.large)
                                        .background(VedaAccentSoft),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .background(VedaSurface),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Apartment,
                                            contentDescription = "Hostel Building",
                                            tint = VedaPrimary,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

                                // Hostel Name
                                Text(
                                    text = hostelNameText,
                                    style = VedaTheme.typography.sectionTitle,
                                    fontWeight = FontWeight.Bold,
                                    color = VedaInk
                                )

                                // Location Pin Row
                                if (locationText.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.LocationOn,
                                            contentDescription = "Location",
                                            tint = VedaPrimary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = locationText,
                                            style = VedaTheme.typography.bodySecondary,
                                            fontWeight = FontWeight.Medium,
                                            color = VedaPrimary
                                        )
                                    }
                                }

                                // Address Display
                                if (formattedAddress.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))
                                    Text(
                                        text = formattedAddress,
                                        style = VedaTheme.typography.bodySecondary,
                                        color = VedaMuted
                                    )
                                }
                            }
                        }
                    }

                    // 2. STUDENT ALLOCATION CARD (ROOM & FLOOR)
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(VedaSpacingInstance.sm),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            HostelDetailSmallCard(
                                icon = Icons.Filled.DoorFront,
                                label = "Room",
                                value = if (student.roomNumber.isNotBlank()) "Room ${student.roomNumber}" else "-",
                                modifier = Modifier.weight(1f)
                            )
                            HostelDetailSmallCard(
                                icon = Icons.Filled.Stairs,
                                label = "Floor",
                                value = if (student.floorNumber.isNotBlank()) "Floor ${student.floorNumber}" else "-",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // 3. HOSTEL INFRASTRUCTURE CAPACITY CARD (TOTAL FLOORS & TOTAL ROOMS)
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(VedaSpacingInstance.sm),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            HostelDetailSmallCard(
                                icon = Icons.Filled.CorporateFare,
                                label = "Total Floors",
                                value = if (hostelInfo.totalFloors > 0) "${hostelInfo.totalFloors} Floors" else "-",
                                modifier = Modifier.weight(1f)
                            )
                            HostelDetailSmallCard(
                                icon = Icons.Filled.DoorFront,
                                label = "Total Rooms",
                                value = if (hostelInfo.totalRooms > 0) "${hostelInfo.totalRooms} Rooms" else "-",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // 4. HOSTEL WARDEN CARD
                    if (wardenNameText.isNotBlank()) {
                        item {
                            VedaCard(
                                modifier = Modifier.fillMaxWidth(),
                                backgroundColor = VedaSurface,
                                borderColor = VedaBorder,
                                shape = VedaShapesInstance.large,
                                contentPadding = VedaSpacingInstance.cardPadding
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(38.dp)
                                                .clip(VedaShapesInstance.small)
                                                .background(VedaAccentSoft),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Person,
                                                contentDescription = "Warden",
                                                tint = VedaPrimary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(VedaSpacingInstance.sm))
                                        Column {
                                            Text(
                                                text = "Hostel Warden",
                                                style = VedaTheme.typography.caption,
                                                color = VedaMuted
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = wardenNameText,
                                                style = VedaTheme.typography.body,
                                                fontWeight = FontWeight.Bold,
                                                color = VedaInk
                                            )
                                        }
                                    }

                                    if (student.wardenPhone.isNotBlank()) {
                                        VedaIconButton(
                                            icon = Icons.Filled.Call,
                                            contentDescription = "Call Warden",
                                            onClick = {
                                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                                    data = Uri.parse("tel:${student.wardenPhone}")
                                                }
                                                context.startActivity(intent)
                                            },
                                            tint = VedaSuccess,
                                            modifier = Modifier
                                                .size(42.dp)
                                                .clip(CircleShape)
                                                .background(VedaSuccessSoft)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 5. ABOUT THE HOSTEL (DESCRIPTION)
                    if (hostelInfo.description.isNotBlank()) {
                        item {
                            VedaCard(
                                modifier = Modifier.fillMaxWidth(),
                                backgroundColor = VedaSurface,
                                borderColor = VedaBorder,
                                shape = VedaShapesInstance.large,
                                contentPadding = VedaSpacingInstance.cardPadding
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "About the Hostel",
                                        style = VedaTheme.typography.sectionTitle,
                                        fontWeight = FontWeight.Bold,
                                        color = VedaInk
                                    )
                                    Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))
                                    Text(
                                        text = hostelInfo.description,
                                        style = VedaTheme.typography.bodySecondary,
                                        color = VedaMuted
                                    )
                                }
                            }
                        }
                    }

                    // 6. IMPORTANT CONTACTS SECTION (PREVIEW)
                    item {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            VedaSectionHeader(
                                title = "Important Contacts",
                                actionText = "View All",
                                onActionClick = onViewAllContactsClick
                            )

                            Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))

                            if (previewContacts.isEmpty()) {
                                VedaEmptyState(
                                    title = "No hostel contacts available yet",
                                    icon = Icons.Filled.PeopleOutline
                                )
                            } else {
                                Column(verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.sm)) {
                                    previewContacts.forEach { contact ->
                                        ContactPreviewCardItem(
                                            contact = contact,
                                            onCallClick = {
                                                if (contact.phoneNumber.isNotBlank()) {
                                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                                        data = Uri.parse("tel:${contact.phoneNumber}")
                                                    }
                                                    context.startActivity(intent)
                                                }
                                            }
                                        )
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        TextButton(onClick = onViewAllContactsClick) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = "View all contacts",
                                                    style = VedaTheme.typography.label,
                                                    fontWeight = FontWeight.Bold,
                                                    color = VedaPrimary
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                                    contentDescription = "View all",
                                                    tint = VedaPrimary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // READ-ONLY NOTICE FOOTNOTE
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = VedaSpacingInstance.sm),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Info",
                                tint = VedaMuted,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Hostel allocation details are managed by Warden administration.",
                                style = VedaTheme.typography.caption,
                                color = VedaMuted
                            )
                        }
                    }

                    // BOTTOM CONTENT PADDING
                    item {
                        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactPreviewCardItem(
    contact: ContactInfo,
    onCallClick: () -> Unit
) {
    VedaCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = VedaSurface,
        borderColor = VedaBorder,
        shape = VedaShapesInstance.large,
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
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(VedaAccentSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Contacts,
                        contentDescription = contact.title,
                        tint = VedaPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(VedaSpacingInstance.sm))
                Column {
                    Text(
                        text = contact.title.ifBlank { contact.name },
                        style = VedaTheme.typography.body,
                        fontWeight = FontWeight.Bold,
                        color = VedaInk
                    )
                    if (contact.phoneNumber.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = contact.phoneNumber,
                            style = VedaTheme.typography.caption,
                            color = VedaMuted
                        )
                    }
                }
            }

            if (contact.phoneNumber.isNotBlank()) {
                VedaIconButton(
                    icon = Icons.Filled.Call,
                    contentDescription = "Call",
                    onClick = onCallClick,
                    tint = VedaSuccess,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(VedaSuccessSoft)
                )
            }
        }
    }
}

@Composable
private fun HostelDetailSmallCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    VedaCard(
        modifier = modifier,
        backgroundColor = VedaSurface,
        borderColor = VedaBorder,
        shape = VedaShapesInstance.large,
        contentPadding = VedaSpacingInstance.cardPadding
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(VedaShapesInstance.small)
                    .background(VedaAccentSoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = VedaPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(VedaSpacingInstance.sm))
            Column {
                Text(
                    text = label,
                    style = VedaTheme.typography.caption,
                    color = VedaMuted
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    style = VedaTheme.typography.body,
                    fontWeight = FontWeight.Bold,
                    color = VedaInk
                )
            }
        }
    }
}

private fun formatAddress(hostelInfo: HostelInfo): String {
    val line1 = hostelInfo.address.trim()
    val cityState = listOfNotNull(
        hostelInfo.city.trim().takeIf { it.isNotBlank() },
        hostelInfo.state.trim().takeIf { it.isNotBlank() }
    ).joinToString(", ")

    val pin = hostelInfo.pinCode.trim().takeIf { it.isNotBlank() }
    val line2 = if (cityState.isNotBlank() && pin != null) {
        "$cityState — $pin"
    } else if (cityState.isNotBlank()) {
        cityState
    } else if (pin != null) {
        pin
    } else {
        ""
    }

    return listOfNotNull(
        line1.takeIf { it.isNotBlank() },
        line2.takeIf { it.isNotBlank() }
    ).joinToString("\n")
}

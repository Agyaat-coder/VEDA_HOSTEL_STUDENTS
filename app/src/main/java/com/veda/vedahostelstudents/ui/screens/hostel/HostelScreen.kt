package com.veda.vedahostelstudents.ui.screens.hostel

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Stairs
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.ContactInfo
import com.veda.vedahostelstudents.data.model.HostelInfo
import com.veda.vedahostelstudents.data.model.MessDayMenu
import com.veda.vedahostelstudents.data.model.Notice
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.ui.components.SegmentedControl
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurfaceVariant
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreen
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun HostelScreen(
    student: Student,
    hostelInfo: HostelInfo,
    notices: List<Notice>,
    contacts: List<ContactInfo>,
    messMenu: MessDayMenu,
    onNoticeClick: (Notice) -> Unit,
    onViewMessMenu: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    var selectedNoticeForDialog by remember { mutableStateOf<Notice?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .padding(20.dp)
    ) {
        // Screen Header Title
        Text(
            text = "Hostel",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = VedaTextPrimary,
            modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
        )

        // Segmented Control: Overview | Notices | Contacts | Mess
        SegmentedControl(
            items = listOf("Overview", "Notices", "Contacts", "Mess"),
            selectedIndex = selectedTab,
            onSegmentSelected = { selectedTab = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        when (selectedTab) {
            0 -> {
                // OVERVIEW TAB
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Hostel Banner Card
                    item {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            color = VedaDarkSurface
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(VedaBrightBlue.copy(alpha = 0.15f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.CorporateFare,
                                            contentDescription = "Hostel Building",
                                            tint = VedaBrightBlue,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(14.dp))
                                    Column {
                                        Text(
                                            text = hostelInfo.name.ifBlank { student.hostelName.ifBlank { "Hostel" } },
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = VedaTextPrimary
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Filled.LocationOn,
                                                contentDescription = "Campus",
                                                tint = VedaBrightBlue,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = hostelInfo.campus.ifBlank { "Main Campus" },
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = VedaBrightBlue
                                            )
                                        }
                                    }
                                }

                                if (hostelInfo.address.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(14.dp))
                                    val fullAddress = listOfNotNull(
                                        hostelInfo.address.takeIf { it.isNotBlank() },
                                        hostelInfo.city.takeIf { it.isNotBlank() },
                                        hostelInfo.state.takeIf { it.isNotBlank() },
                                        hostelInfo.pinCode.takeIf { it.isNotBlank() }
                                    ).joinToString(", ")

                                    Text(
                                        text = fullAddress,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = VedaTextMuted
                                    )
                                }
                            }
                        }
                    }

                    // Grid Details Cards (Room, Floor, Warden)
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                HostelDetailSmallCard(
                                    icon = Icons.Filled.CorporateFare,
                                    label = "Room",
                                    value = student.roomNumber.ifBlank { "-" },
                                    modifier = Modifier.weight(1f)
                                )
                                HostelDetailSmallCard(
                                    icon = Icons.Filled.Stairs,
                                    label = "Floor",
                                    value = student.floorNumber.ifBlank { "-" },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            if (hostelInfo.wardenName.isNotBlank() || student.wardenName.isNotBlank()) {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    color = VedaDarkSurface
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
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
                                                    imageVector = Icons.Filled.Person,
                                                    contentDescription = "Warden",
                                                    tint = VedaBrightBlue,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Text(
                                                text = "Warden",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = VedaTextMuted
                                            )
                                        }
                                        Text(
                                            text = hostelInfo.wardenName.ifBlank { student.wardenName },
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = VedaTextPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Hostel Description
                    if (hostelInfo.description.isNotBlank()) {
                        item {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                color = VedaDarkSurface
                            ) {
                                Column(modifier = Modifier.padding(18.dp)) {
                                    Text(
                                        text = "About Hostel",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VedaTextPrimary
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = hostelInfo.description,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = VedaTextMuted,
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Info",
                                tint = VedaTextMuted,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Hostel allocation details are managed by Warden office.",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = VedaTextMuted
                            )
                        }
                    }
                }
            }

            1 -> {
                // NOTICES TAB
                if (notices.isEmpty()) {
                    EmptySectionCard(
                        title = "No notices right now",
                        subtitle = "Important hostel updates will appear here."
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(notices) { notice ->
                            NoticeItemCard(
                                notice = notice,
                                onClick = {
                                    onNoticeClick(notice)
                                    selectedNoticeForDialog = notice
                                }
                            )
                        }
                    }
                }
            }

            2 -> {
                // CONTACTS TAB
                val displayContacts = if (contacts.isNotEmpty()) contacts else hostelInfo.contacts
                if (displayContacts.isEmpty()) {
                    EmptySectionCard(
                        title = "No contacts available",
                        subtitle = "Hostel contact information will appear here."
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(displayContacts) { contact ->
                            ContactCardItem(
                                contact = contact,
                                onCallClick = {
                                    if (contact.phoneNumber.isNotBlank()) {
                                        val intent = Intent(Intent.ACTION_DIAL).apply {
                                            data = Uri.parse("tel:${contact.phoneNumber}")
                                        }
                                        context.startActivity(intent)
                                    }
                                },
                                onEmailClick = {
                                    if (contact.email.isNotBlank()) {
                                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("mailto:${contact.email}")
                                        }
                                        context.startActivity(intent)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            3 -> {
                // MESS TAB
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (!messMenu.isPublished || messMenu.meals.isEmpty()) {
                        EmptySectionCard(
                            title = "Today's menu is not available",
                            subtitle = "Check back later for the updated mess menu."
                        )
                    } else {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            color = VedaDarkSurface
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = "Today's Mess Menu",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VedaTextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = messMenu.dateText,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = VedaBrightBlue
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                messMenu.meals.forEach { meal ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 6.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(VedaDarkSurfaceVariant)
                                            .padding(12.dp)
                                    ) {
                                        Column {
                                            Text(
                                                text = meal.category,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = VedaBrightBlue
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = meal.items.joinToString(" • "),
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Normal,
                                                color = VedaTextPrimary
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = onViewMessMenu,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = VedaBrightBlue,
                                        contentColor = VedaTextPrimary
                                    )
                                ) {
                                    Text(
                                        text = "View Full Menu Details",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Notice Detail Dialog
        if (selectedNoticeForDialog != null) {
            val notice = selectedNoticeForDialog!!
            AlertDialog(
                onDismissRequest = { selectedNoticeForDialog = null },
                confirmButton = {
                    TextButton(onClick = { selectedNoticeForDialog = null }) {
                        Text("Close", color = VedaBrightBlue, fontWeight = FontWeight.Bold)
                    }
                },
                title = {
                    Text(
                        text = notice.title,
                        color = VedaTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "Category: ${notice.category}",
                            color = VedaBrightBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = notice.description,
                            color = VedaTextPrimary,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Published by ${notice.publisher} • ${notice.dateText}",
                            color = VedaTextMuted,
                            fontSize = 12.sp
                        )
                    }
                },
                containerColor = VedaDarkSurface,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
private fun EmptySectionCard(
    title: String,
    subtitle: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = VedaDarkSurface
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
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
                    imageVector = Icons.Filled.NotificationsNone,
                    contentDescription = title,
                    tint = VedaBrightBlue,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = VedaTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = VedaTextMuted,
                textAlign = TextAlign.Center
            )
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
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = VedaDarkSurface
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(VedaBrightBlue.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = VedaBrightBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = VedaTextMuted
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = VedaTextPrimary
                )
            }
        }
    }
}

@Composable
private fun ContactCardItem(
    contact: ContactInfo,
    onCallClick: () -> Unit,
    onEmailClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = VedaDarkSurface
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(VedaBrightBlue.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Contacts,
                        contentDescription = contact.title,
                        tint = VedaBrightBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = contact.title.ifBlank { contact.name },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = VedaTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    val detailSubtitle = listOfNotNull(
                        contact.name.takeIf { it.isNotBlank() && it != contact.title },
                        contact.role.takeIf { it.isNotBlank() },
                        contact.department.takeIf { it.isNotBlank() }
                    ).joinToString(" • ")

                    if (detailSubtitle.isNotBlank()) {
                        Text(
                            text = detailSubtitle,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = VedaTextMuted
                        )
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (contact.phoneNumber.isNotBlank()) {
                    IconButton(
                        onClick = onCallClick,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(VedaSuccessGreen.copy(alpha = 0.15f))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = "Call",
                            tint = VedaSuccessGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                if (contact.email.isNotBlank()) {
                    IconButton(
                        onClick = onEmailClick,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(VedaBrightBlue.copy(alpha = 0.15f))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Email",
                            tint = VedaBrightBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoticeItemCard(
    notice: Notice,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = VedaDarkSurface
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
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
                        .clip(RoundedCornerShape(10.dp))
                        .background(VedaBrightBlue.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notice",
                        tint = VedaBrightBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = notice.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = VedaTextPrimary
                        )
                        if (notice.isUnread) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(VedaBrightBlue)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = notice.dateText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = VedaTextMuted
                    )
                }
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Open",
                tint = VedaTextMuted
            )
        }
    }
}

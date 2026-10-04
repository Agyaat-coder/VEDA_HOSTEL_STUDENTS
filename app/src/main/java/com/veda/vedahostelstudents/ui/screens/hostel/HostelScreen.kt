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
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Stairs
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.ContactInfo
import com.veda.vedahostelstudents.data.model.HostelInfo
import com.veda.vedahostelstudents.data.model.Notice
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.ui.components.SegmentedControl
import com.veda.vedahostelstudents.ui.screens.activity.NoticeItemCard
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreen
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun HostelScreen(
    student: Student,
    hostelInfo: HostelInfo,
    notices: List<Notice>,
    onNoticeClick: (Notice) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

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

        // Segmented Control: Overview | Notices
        SegmentedControl(
            items = listOf("Overview", "Notices"),
            selectedIndex = selectedTab,
            onSegmentSelected = { selectedTab = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (selectedTab == 0) {
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
                                        text = student.hostelName,
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
                                            text = hostelInfo.campus,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = VedaBrightBlue
                                        )
                                    }
                                }
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
                                value = student.roomNumber,
                                modifier = Modifier.weight(1f)
                            )
                            HostelDetailSmallCard(
                                icon = Icons.Filled.Stairs,
                                label = "Floor",
                                value = student.floorNumber,
                                modifier = Modifier.weight(1f)
                            )
                        }

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
                                    text = student.wardenName,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VedaTextPrimary
                                )
                            }
                        }
                    }
                }

                // Important Contacts Section
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Important Contacts",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = VedaTextPrimary
                    )
                }

                items(hostelInfo.contacts) { contact ->
                    ContactCardItem(
                        contact = contact,
                        onCallClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${contact.phoneNumber}")
                            }
                            context.startActivity(intent)
                        }
                    )
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
        } else {
            // NOTICES TAB
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notices) { notice ->
                    NoticeItemCard(
                        notice = notice,
                        onClick = { onNoticeClick(notice) }
                    )
                }
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
    onCallClick: () -> Unit
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
            Column {
                Text(
                    text = contact.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = VedaTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${contact.name} • ${contact.phoneNumber}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = VedaTextMuted
                )
            }

            IconButton(
                onClick = onCallClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(VedaSuccessGreen.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Call",
                    tint = VedaSuccessGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

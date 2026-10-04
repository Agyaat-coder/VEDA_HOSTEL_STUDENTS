package com.veda.vedahostelstudents.ui.screens.activity

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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.AttendanceRecord
import com.veda.vedahostelstudents.data.model.AttendanceSessionType
import com.veda.vedahostelstudents.data.model.Notice
import com.veda.vedahostelstudents.ui.components.StatusChip
import com.veda.vedahostelstudents.ui.theme.VedaAlertRed
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurfaceVariant
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreen
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun ActivityScreen(
    attendanceHistory: List<AttendanceRecord>,
    onViewAllAttendance: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .padding(20.dp)
    ) {
        // Screen Header Title
        Text(
            text = "Activity",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = VedaTextPrimary,
            modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Attendance Summary Card
            item {
                val total = attendanceHistory.size
                val presentCount = attendanceHistory.count { it.status == com.veda.vedahostelstudents.data.model.AttendanceStatus.PRESENT }
                val missedCount = total - presentCount
                val percentage = if (total > 0) ((presentCount.toFloat() / total.toFloat()) * 100).toInt() else 100

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = VedaDarkSurface
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Attendance Summary",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = VedaTextPrimary
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Circular Progress Ring
                            Box(
                                modifier = Modifier.size(90.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    progress = { percentage / 100f },
                                    modifier = Modifier.fillMaxSize(),
                                    color = VedaSuccessGreen,
                                    trackColor = VedaDarkSurfaceVariant,
                                    strokeWidth = 8.dp
                                )
                                Text(
                                    text = "$percentage%",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VedaTextPrimary
                                )
                            }

                            // Stats Numbers Breakdown
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                StatSummaryRow(
                                    label = "Present",
                                    value = "$presentCount",
                                    color = VedaSuccessGreen
                                )
                                StatSummaryRow(
                                    label = "Missed",
                                    value = "$missedCount",
                                    color = VedaAlertRed
                                )
                                StatSummaryRow(
                                    label = "Total",
                                    value = "$total",
                                    color = VedaTextPrimary
                                )
                            }
                        }
                    }
                }
            }

            // Recent Activity Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Activity",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = VedaTextPrimary
                    )
                    Text(
                        text = "View All",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VedaBrightBlue,
                        modifier = Modifier.clickable { onViewAllAttendance() }
                    )
                }
            }

            // Recent Activity List Items
            items(attendanceHistory.take(5)) { record ->
                AttendanceRecordItem(record = record)
            }
        }
    }
}

@Composable
private fun StatSummaryRow(
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "$label:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = VedaTextMuted,
            modifier = Modifier.width(60.dp)
        )
        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun AttendanceRecordItem(
    record: AttendanceRecord,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = VedaDarkSurface
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val icon = if (record.sessionType == AttendanceSessionType.MORNING) {
                    Icons.Filled.WbSunny
                } else {
                    Icons.Filled.NightsStay
                }

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(VedaBrightBlue.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Session",
                        tint = VedaBrightBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = record.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = VedaTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = record.dateTimeText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = VedaTextMuted
                    )
                }
            }

            StatusChip(status = record.status)
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

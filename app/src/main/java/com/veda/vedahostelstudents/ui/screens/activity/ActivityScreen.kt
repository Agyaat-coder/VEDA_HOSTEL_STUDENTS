package com.veda.vedahostelstudents.ui.screens.activity

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.AttendanceRecord
import com.veda.vedahostelstudents.data.model.AttendanceSessionType
import com.veda.vedahostelstudents.ui.components.StatusChip
import com.veda.vedahostelstudents.ui.theme.VedaAlertRed
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun ActivityScreen(
    attendanceHistory: List<AttendanceRecord>,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onViewAllAttendance: () -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredList = when (selectedFilter) {
        "Morning" -> attendanceHistory.filter { it.sessionType == AttendanceSessionType.MORNING }
        "Evening" -> attendanceHistory.filter { it.sessionType == AttendanceSessionType.EVENING }
        else -> attendanceHistory
    }

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

        // Session Filter Chips
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("All", "Morning", "Evening").forEach { filter ->
                val isSelected = selectedFilter == filter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                    label = { Text(text = filter, fontSize = 13.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VedaBrightBlue,
                        selectedLabelColor = VedaTextPrimary,
                        containerColor = VedaDarkSurface,
                        labelColor = VedaTextMuted
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            // STATE 1: LOADING
            isLoading && attendanceHistory.isEmpty() -> {
                LoadingAttendanceHistoryView()
            }

            // STATE 2: ERROR
            errorMessage != null && attendanceHistory.isEmpty() -> {
                ErrorAttendanceHistoryView(message = errorMessage)
            }

            // STATE 3: EMPTY HISTORY
            filteredList.isEmpty() -> {
                EmptyAttendanceHistoryView()
            }

            // STATE 4: ATTENDANCE HISTORY LIST
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredList) { record ->
                        AttendanceRecordItem(record = record)
                    }
                }
            }
        }
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Top Row: Date & Status Chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = record.dateText.ifBlank { record.dateTimeText },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = VedaTextPrimary
                )

                StatusChip(status = record.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom Row: Session & Marked Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val icon = if (record.sessionType == AttendanceSessionType.MORNING) {
                        Icons.Filled.WbSunny
                    } else {
                        Icons.Filled.NightsStay
                    }

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(VedaBrightBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = record.title,
                            tint = VedaBrightBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = record.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = VedaTextMuted
                    )
                }

                if (record.timeText.isNotBlank()) {
                    Text(
                        text = record.timeText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = VedaTextMuted
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyAttendanceHistoryView() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = VedaDarkSurface
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(VedaBrightBlue.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.EventNote,
                    contentDescription = "No History",
                    tint = VedaBrightBlue,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No attendance history yet",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = VedaTextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Your attendance records will appear here after you mark your presence.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = VedaTextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun LoadingAttendanceHistoryView() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = VedaDarkSurface
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(36.dp),
                color = VedaBrightBlue,
                strokeWidth = 3.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Loading attendance history...",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = VedaTextMuted
            )
        }
    }
}

@Composable
fun ErrorAttendanceHistoryView(message: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = VedaDarkSurface
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(VedaAlertRed.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Error",
                    tint = VedaAlertRed,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Unable to load attendance history.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = VedaTextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = message.ifBlank { "Please check your internet connection and try again." },
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = VedaTextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.data.model.AttendanceRecord
import com.veda.vedahostelstudents.data.model.AttendanceSessionType
import com.veda.vedahostelstudents.ui.components.SegmentedControl
import com.veda.vedahostelstudents.ui.components.StatusChip
import com.veda.vedahostelstudents.ui.components.VedaCard
import com.veda.vedahostelstudents.ui.components.VedaEmptyState
import com.veda.vedahostelstudents.ui.components.VedaErrorState
import com.veda.vedahostelstudents.ui.components.VedaLoadingState
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
fun ActivityScreen(
    attendanceHistory: List<AttendanceRecord>,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onViewAllAttendance: () -> Unit = {}
) {
    val filters = remember { listOf("All", "Morning", "Evening") }
    var selectedFilterIndex by remember { mutableStateOf(0) }

    val filteredList = when (selectedFilterIndex) {
        1 -> attendanceHistory.filter { it.sessionType == AttendanceSessionType.MORNING }
        2 -> attendanceHistory.filter { it.sessionType == AttendanceSessionType.EVENING }
        else -> attendanceHistory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaCanvas)
            .padding(horizontal = VedaSpacingInstance.screenPaddingHorizontal)
    ) {
        Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

        // Screen Header & Subtitle (Pages 27 & 33)
        Column(modifier = Modifier.padding(vertical = VedaSpacingInstance.md)) {
            Text(
                text = "Activity",
                style = VedaTheme.typography.screenTitle,
                color = VedaInk
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Your attendance, one day at a time.",
                style = VedaTheme.typography.bodySecondary,
                color = VedaMuted
            )
        }

        // Segmented Control Filters (All, Morning, Evening)
        SegmentedControl(
            items = filters,
            selectedIndex = selectedFilterIndex,
            onSegmentSelected = { selectedFilterIndex = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.lg))

        when {
            // STATE 1: LOADING
            isLoading && attendanceHistory.isEmpty() -> {
                VedaLoadingState(message = "Loading attendance journal...")
            }

            // STATE 2: ERROR
            errorMessage != null && attendanceHistory.isEmpty() -> {
                VedaErrorState(
                    title = "Let's try that again",
                    message = errorMessage
                )
            }

            // STATE 3: EMPTY JOURNAL
            filteredList.isEmpty() -> {
                VedaEmptyState(
                    title = "Your journal starts here",
                    message = "Your check-in history will appear here after your first attendance session.",
                    icon = Icons.AutoMirrored.Filled.EventNote
                )
            }

            // STATE 4: ATTENDANCE TIMELINE JOURNAL (PAGE 41)
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.md)
                ) {
                    items(filteredList) { record ->
                        VedaTimelineItem(record = record)
                    }
                    item {
                        Spacer(modifier = Modifier.height(VedaSpacingInstance.xxl))
                    }
                }
            }
        }
    }
}

@Composable
fun VedaTimelineItem(
    record: AttendanceRecord,
    modifier: Modifier = Modifier
) {
    VedaCard(
        modifier = modifier.fillMaxWidth(),
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
                // Timeline Date Badge / Icon Box
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(VedaShapesInstance.medium)
                        .background(VedaAccentSoft),
                    contentAlignment = Alignment.Center
                ) {
                    val icon = if (record.sessionType == AttendanceSessionType.MORNING) {
                        Icons.Filled.WbSunny
                    } else {
                        Icons.Filled.NightsStay
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = record.title,
                        tint = VedaPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(VedaSpacingInstance.md))

                Column {
                    Text(
                        text = record.dateText.ifBlank { record.dateTimeText },
                        style = VedaTheme.typography.caption,
                        color = VedaPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = record.title,
                        style = VedaTheme.typography.body,
                        fontWeight = FontWeight.Bold,
                        color = VedaInk
                    )
                    if (record.timeText.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = record.timeText,
                            style = VedaTheme.typography.caption,
                            color = VedaMuted
                        )
                    }
                }
            }

            StatusChip(status = record.status)
        }
    }
}

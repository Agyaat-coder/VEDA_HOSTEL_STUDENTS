package com.veda.vedahostelstudents.ui.screens.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.veda.vedahostelstudents.data.model.AttendanceRecord
import com.veda.vedahostelstudents.data.model.AttendanceSessionType
import com.veda.vedahostelstudents.ui.components.SegmentedControl
import com.veda.vedahostelstudents.ui.components.VedaEmptyState
import com.veda.vedahostelstudents.ui.components.VedaIconButton
import com.veda.vedahostelstudents.ui.theme.VedaCanvas
import com.veda.vedahostelstudents.ui.theme.VedaInk
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaTheme

@Composable
fun AttendanceHistoryScreen(
    attendanceHistory: List<AttendanceRecord>,
    onBack: () -> Unit
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
                text = "Attendance History",
                style = VedaTheme.typography.screenTitle,
                fontWeight = FontWeight.Bold,
                color = VedaInk
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        // Segmented Control Filter Row (All, Morning, Evening)
        SegmentedControl(
            items = filters,
            selectedIndex = selectedFilterIndex,
            onSegmentSelected = { selectedFilterIndex = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.lg))

        if (filteredList.isEmpty()) {
            VedaEmptyState(
                title = "No attendance history yet",
                message = "Your attendance records will appear here after you mark your presence.",
                icon = Icons.AutoMirrored.Filled.EventNote
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.listSpacing)
            ) {
                items(filteredList) { record ->
                    VedaTimelineItem(record = record)
                }
                item {
                    Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))
                }
            }
        }
    }
}

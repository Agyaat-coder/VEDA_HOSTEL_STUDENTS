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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.AttendanceRecord
import com.veda.vedahostelstudents.data.model.AttendanceStatus
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun AttendanceHistoryScreen(
    attendanceHistory: List<AttendanceRecord>,
    onBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredList = when (selectedFilter) {
        "Present" -> attendanceHistory.filter { it.status == AttendanceStatus.PRESENT }
        "Missed" -> attendanceHistory.filter { it.status == AttendanceStatus.MISSED }
        else -> attendanceHistory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .padding(20.dp)
    ) {
        // Top Nav Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = VedaTextPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Attendance History",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VedaTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filter Chips Row (All, Present, Missed)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("All", "Present", "Missed").forEach { filter ->
                val isSelected = selectedFilter == filter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                    label = { Text(text = filter, fontSize = 13.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VedaBrightBlue,
                        selectedLabelColor = VedaTextPrimary,
                        containerColor = VedaDarkBackground,
                        labelColor = VedaTextMuted
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Full History List
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

package com.veda.vedahostelstudents.ui.screens.profile

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun AccountDetailsScreen(
    student: Student,
    onBack: () -> Unit
) {
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
                text = "Account Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VedaTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Student Info Cards List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = VedaDarkSurface
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        DetailFieldRow(label = "Student ID", value = student.id)
                        DetailFieldRow(label = "Roll Number", value = student.rollNumber)
                        DetailFieldRow(label = "Full Name", value = student.name)
                        DetailFieldRow(label = "Course", value = student.course)
                        DetailFieldRow(label = "Branch", value = student.branch)
                        DetailFieldRow(label = "Year", value = student.year)
                        DetailFieldRow(label = "Hostel", value = student.hostelName)
                        DetailFieldRow(label = "Room Number", value = student.roomNumber)
                        DetailFieldRow(label = "Floor", value = "Floor ${student.floorNumber}")
                    }
                }
            }

            item {
                Text(
                    text = "🔒 Administrative student details are read-only and maintained by Warden administration.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = VedaTextMuted,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun DetailFieldRow(
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
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = VedaTextPrimary
        )
    }
}

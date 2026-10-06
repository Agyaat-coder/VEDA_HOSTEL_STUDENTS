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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.ui.components.VedaCard
import com.veda.vedahostelstudents.ui.components.VedaIconButton
import com.veda.vedahostelstudents.ui.theme.VedaBorder
import com.veda.vedahostelstudents.ui.theme.VedaCanvas
import com.veda.vedahostelstudents.ui.theme.VedaInk
import com.veda.vedahostelstudents.ui.theme.VedaMuted
import com.veda.vedahostelstudents.ui.theme.VedaShapesInstance
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaSurface
import com.veda.vedahostelstudents.ui.theme.VedaTheme

@Composable
fun AccountDetailsScreen(
    student: Student,
    onBack: () -> Unit
) {
    val displayName = student.fullName.ifBlank { student.name }

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
                text = "Account Details",
                style = VedaTheme.typography.screenTitle,
                fontWeight = FontWeight.Bold,
                color = VedaInk
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        // Student Info Cards List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.listSpacing)
        ) {
            item {
                VedaCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = VedaSurface,
                    borderColor = VedaBorder,
                    shape = VedaShapesInstance.large,
                    contentPadding = VedaSpacingInstance.cardPadding
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.md)
                    ) {
                        DetailFieldRow(label = "Student ID", value = student.studentId.ifBlank { student.id.ifBlank { "Not available" } })
                        DetailFieldRow(label = "Roll Number", value = student.rollNumber.ifBlank { "Not available" })
                        DetailFieldRow(label = "Full Name", value = displayName.ifBlank { "Not available" })
                        DetailFieldRow(label = "Email", value = student.email.ifBlank { "Not available" })
                        DetailFieldRow(label = "Phone", value = student.phoneNumber.ifBlank { "Not available" })
                        DetailFieldRow(label = "Course", value = student.course.ifBlank { "Not available" })
                        DetailFieldRow(label = "Branch", value = student.branch.ifBlank { "Not available" })
                        DetailFieldRow(label = "Year", value = student.year.ifBlank { "Not available" })
                        DetailFieldRow(label = "Hostel", value = student.hostelName.ifBlank { "Not available" })
                        DetailFieldRow(label = "Room Number", value = if (student.roomNumber.isNotBlank()) "Room ${student.roomNumber}" else "Not available")
                        DetailFieldRow(label = "Floor", value = if (student.floorNumber.isNotBlank()) "Floor ${student.floorNumber}" else "Not available")
                    }
                }
            }

            item {
                Text(
                    text = "🔒 Administrative student details are read-only and maintained by Warden administration.",
                    style = VedaTheme.typography.caption,
                    color = VedaMuted,
                    modifier = Modifier.padding(horizontal = VedaSpacingInstance.xs)
                )
            }

            item {
                Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))
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
            style = VedaTheme.typography.caption,
            color = VedaMuted
        )
        Text(
            text = value,
            style = VedaTheme.typography.bodySecondary,
            fontWeight = FontWeight.Bold,
            color = VedaInk
        )
    }
}

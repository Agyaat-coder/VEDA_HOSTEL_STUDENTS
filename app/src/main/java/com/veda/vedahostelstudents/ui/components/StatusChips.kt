package com.veda.vedahostelstudents.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.AttendanceStatus
import com.veda.vedahostelstudents.ui.theme.VedaAlertRed
import com.veda.vedahostelstudents.ui.theme.VedaAlertRedBg
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreen
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreenBg

@Composable
fun StatusChip(
    status: AttendanceStatus,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, text) = when (status) {
        AttendanceStatus.PRESENT -> Triple(VedaSuccessGreenBg, VedaSuccessGreen, "Present")
        AttendanceStatus.MISSED -> Triple(VedaAlertRedBg, VedaAlertRed, "Missed")
        AttendanceStatus.PENDING -> Triple(VedaSuccessGreenBg, VedaSuccessGreen, "Pending")
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

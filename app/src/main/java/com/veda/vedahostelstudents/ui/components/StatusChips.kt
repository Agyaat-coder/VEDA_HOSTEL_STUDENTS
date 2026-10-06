package com.veda.vedahostelstudents.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.veda.vedahostelstudents.data.model.AttendanceStatus

@Composable
fun StatusChip(
    status: AttendanceStatus,
    modifier: Modifier = Modifier
) {
    val (style, text) = when (status) {
        AttendanceStatus.PRESENT -> VedaStatusStyle.PRESENT to "PRESENT"
        AttendanceStatus.ABSENT -> VedaStatusStyle.ABSENT to "ABSENT"
        AttendanceStatus.NOT_MARKED -> VedaStatusStyle.NOT_MARKED to "NOT MARKED"
    }

    VedaStatusBadge(
        text = text,
        style = style,
        modifier = modifier
    )
}

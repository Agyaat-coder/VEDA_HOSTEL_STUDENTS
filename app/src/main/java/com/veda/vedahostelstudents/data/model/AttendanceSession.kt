package com.veda.vedahostelstudents.data.model

import java.util.Date

enum class AttendanceSessionType(val displayName: String) {
    MORNING("Morning Attendance"),
    EVENING("Evening Attendance")
}

enum class SessionStatus {
    ACTIVE,
    CLOSED,
    UPCOMING
}

data class AttendanceSession(
    val id: String = "",
    val sessionType: AttendanceSessionType = AttendanceSessionType.EVENING,
    val title: String = sessionType.displayName,
    val hostelName: String = "",
    val dateText: String = "",
    val startTimeText: String = "",
    val endTimeText: String = "",
    val status: SessionStatus = SessionStatus.ACTIVE,
    val cutoffTimeText: String = ""
)

enum class AttendanceStatus {
    PRESENT,
    MISSED,
    PENDING
}

data class AttendanceRecord(
    val id: String = "",
    val recordId: String = id,
    val sessionId: String = "",
    val sessionType: AttendanceSessionType = AttendanceSessionType.EVENING,
    val title: String = sessionType.displayName,
    val dateTimeText: String = "",
    val dateText: String = "",
    val timeText: String = "",
    val monthYearText: String = "",
    val status: AttendanceStatus = AttendanceStatus.PRESENT,
    val markedAt: Date? = null
)

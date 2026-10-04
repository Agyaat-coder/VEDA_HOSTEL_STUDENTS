package com.veda.vedahostelstudents.data.model

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
    val id: String = "SESS_2026_1002_EVENING",
    val sessionType: AttendanceSessionType = AttendanceSessionType.EVENING,
    val title: String = sessionType.displayName,
    val hostelName: String = "Charak Chatras",
    val dateText: String = "2 Oct 2026",
    val startTimeText: String = "08:00 PM",
    val endTimeText: String = "10:30 PM",
    val status: SessionStatus = SessionStatus.ACTIVE,
    val cutoffTimeText: String = "10:30 PM"
)

enum class AttendanceStatus {
    PRESENT,
    MISSED,
    PENDING
}

data class AttendanceRecord(
    val id: String = "",
    val sessionId: String = "",
    val sessionType: AttendanceSessionType = AttendanceSessionType.EVENING,
    val title: String = sessionType.displayName,
    val dateTimeText: String = "2 Oct 2026 • 08:12 PM",
    val dateText: String = "2 Oct 2026",
    val timeText: String = "08:12 PM",
    val monthYearText: String = "October 2026",
    val status: AttendanceStatus = AttendanceStatus.PRESENT
)

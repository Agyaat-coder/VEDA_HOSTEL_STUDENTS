package com.veda.vedahostelstudents.data.repository

import android.content.Context
import com.veda.vedahostelstudents.data.model.AttendanceRecord
import com.veda.vedahostelstudents.data.model.AttendanceSession
import com.veda.vedahostelstudents.data.model.AttendanceSessionType
import com.veda.vedahostelstudents.data.model.AttendanceStatus
import com.veda.vedahostelstudents.data.model.HostelInfo
import com.veda.vedahostelstudents.data.model.MealItem
import com.veda.vedahostelstudents.data.model.MessDayMenu
import com.veda.vedahostelstudents.data.model.Notice
import com.veda.vedahostelstudents.data.model.SessionStatus
import com.veda.vedahostelstudents.data.model.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ActivationResult {
    SUCCESS,
    INVALID_CODE,
    ALREADY_USED
}

object HostelRepository {

    private const val PREFS_NAME = "veda_student_prefs"
    private const val KEY_IS_ACTIVATED = "is_activated"
    private const val KEY_STUDENT_ID = "student_id"
    private const val KEY_STUDENT_NAME = "student_name"
    private const val KEY_HOSTEL_NAME = "hostel_name"
    private const val KEY_ROOM_NUMBER = "room_number"
    private const val KEY_FLOOR_NUMBER = "floor_number"

    // Current Student State
    private val _student = MutableStateFlow(Student(isActivated = false))
    val student: StateFlow<Student> = _student.asStateFlow()

    // Initialize repository and check persisted activation state from SharedPreferences
    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isActivated = prefs.getBoolean(KEY_IS_ACTIVATED, false)
        if (isActivated) {
            val id = prefs.getString(KEY_STUDENT_ID, "24AIM001") ?: "24AIM001"
            val name = prefs.getString(KEY_STUDENT_NAME, "Devesh Dwivedi") ?: "Devesh Dwivedi"
            val hostel = prefs.getString(KEY_HOSTEL_NAME, "Charak Chatras") ?: "Charak Chatras"
            val room = prefs.getString(KEY_ROOM_NUMBER, "214") ?: "214"
            val floor = prefs.getString(KEY_FLOOR_NUMBER, "2") ?: "2"

            _student.value = Student(
                id = id,
                rollNumber = "2024-AI-001",
                name = name,
                hostelName = hostel,
                roomNumber = room,
                floorNumber = floor,
                course = "B.Tech",
                branch = "Artificial Intelligence",
                year = "2nd Year",
                isActivated = true,
                activationCode = "VD1234"
            )
        } else {
            _student.value = Student(isActivated = false)
        }
    }

    // Active Attendance Session State (null if no active session)
    private val _activeSession = MutableStateFlow<AttendanceSession?>(null)
    val activeSession: StateFlow<AttendanceSession?> = _activeSession.asStateFlow()

    // Recorded Attendance History (MORNING and EVENING sessions only)
    private val _attendanceHistory = MutableStateFlow<List<AttendanceRecord>>(
        listOf(
            AttendanceRecord(
                id = "1",
                sessionId = "SESS_1001_MORN",
                sessionType = AttendanceSessionType.MORNING,
                title = "Morning Attendance",
                dateTimeText = "2 Oct 2026 • 07:42 AM",
                dateText = "2 Oct 2026",
                timeText = "07:42 AM",
                monthYearText = "October 2026",
                status = AttendanceStatus.PRESENT
            ),
            AttendanceRecord(
                id = "2",
                sessionId = "SESS_1000_EVE",
                sessionType = AttendanceSessionType.EVENING,
                title = "Evening Attendance",
                dateTimeText = "1 Oct 2026 • 08:05 PM",
                dateText = "1 Oct 2026",
                timeText = "08:05 PM",
                monthYearText = "October 2026",
                status = AttendanceStatus.PRESENT
            ),
            AttendanceRecord(
                id = "3",
                sessionId = "SESS_0999_MORN",
                sessionType = AttendanceSessionType.MORNING,
                title = "Morning Attendance",
                dateTimeText = "1 Oct 2026 • 07:51 AM",
                dateText = "1 Oct 2026",
                timeText = "07:51 AM",
                monthYearText = "October 2026",
                status = AttendanceStatus.PRESENT
            ),
            AttendanceRecord(
                id = "4",
                sessionId = "SESS_0998_EVE",
                sessionType = AttendanceSessionType.EVENING,
                title = "Evening Attendance",
                dateTimeText = "29 Sep 2026 • 08:10 PM",
                dateText = "29 Sep 2026",
                timeText = "08:10 PM",
                monthYearText = "September 2026",
                status = AttendanceStatus.MISSED
            ),
            AttendanceRecord(
                id = "5",
                sessionId = "SESS_0997_MORN",
                sessionType = AttendanceSessionType.MORNING,
                title = "Morning Attendance",
                dateTimeText = "29 Sep 2026 • 07:45 AM",
                dateText = "29 Sep 2026",
                timeText = "07:45 AM",
                monthYearText = "September 2026",
                status = AttendanceStatus.PRESENT
            ),
            AttendanceRecord(
                id = "6",
                sessionId = "SESS_0996_EVE",
                sessionType = AttendanceSessionType.EVENING,
                title = "Evening Attendance",
                dateTimeText = "28 Sep 2026 • 08:15 PM",
                dateText = "28 Sep 2026",
                timeText = "08:15 PM",
                monthYearText = "September 2026",
                status = AttendanceStatus.PRESENT
            ),
            AttendanceRecord(
                id = "7",
                sessionId = "SESS_0995_MORN",
                sessionType = AttendanceSessionType.MORNING,
                title = "Morning Attendance",
                dateTimeText = "28 Sep 2026 • 07:50 AM",
                dateText = "28 Sep 2026",
                timeText = "07:50 AM",
                monthYearText = "September 2026",
                status = AttendanceStatus.PRESENT
            )
        )
    )
    val attendanceHistory: StateFlow<List<AttendanceRecord>> = _attendanceHistory.asStateFlow()

    // Has Marked Current Active Session
    private val _hasMarkedCurrentSession = MutableStateFlow(false)
    val hasMarkedCurrentSession: StateFlow<Boolean> = _hasMarkedCurrentSession.asStateFlow()

    // Notices State
    private val _notices = MutableStateFlow<List<Notice>>(
        listOf(
            Notice("N1", "Water Supply Maintenance", "Water supply will be paused tomorrow from 10:00 AM to 1:00 PM for tank cleaning.", "Maintenance", "2 Oct 2026", true),
            Notice("N2", "Special Mess Menu on Gandhi Jayanti", "Special lunch feast will be served in the central dining hall.", "Mess", "2 Oct 2026", true),
            Notice("N3", "Late Entry Gate Pass Guidelines", "Students requiring late entry pass must apply 2 hours prior via Warden office.", "Announcement", "28 Sep 2026", false),
            Notice("N4", "Wi-Fi Maintenance Schedule", "Hostel Wi-Fi routers will undergo routine firmware updates tonight at 12 AM.", "General", "25 Sep 2026", false)
        )
    )
    val notices: StateFlow<List<Notice>> = _notices.asStateFlow()

    // Mess Menu
    private val _messMenu = MutableStateFlow(
        MessDayMenu(
            dayName = "Today (2 Oct 2026)",
            dateText = "2 Oct 2026",
            meals = listOf(
                MealItem("Breakfast", "07:30 AM – 09:30 AM", listOf("Poha", "Boiled Eggs / Sprouts", "Masala Chai", "Banana")),
                MealItem("Lunch", "12:30 PM – 02:30 PM", listOf("Paneer Butter Masala", "Dal Tadka", "Jeera Rice", "Butter Roti", "Gulab Jamun"), "Festive Special"),
                MealItem("Evening Snacks", "05:00 PM – 06:00 PM", listOf("Samosa with Mint Chutney", "Coffee / Tea")),
                MealItem("Dinner", "08:00 PM – 09:30 PM", listOf("Mix Veg Curry", "Arhar Dal", "Plain Rice", "Chapati", "Kheer"))
            )
        )
    )
    val messMenu: StateFlow<MessDayMenu> = _messMenu.asStateFlow()

    // Hostel Information
    private val _hostelInfo = MutableStateFlow(HostelInfo())
    val hostelInfo: StateFlow<HostelInfo> = _hostelInfo.asStateFlow()

    // Notification Toggles
    private val _attendanceRemindersEnabled = MutableStateFlow(true)
    val attendanceRemindersEnabled: StateFlow<Boolean> = _attendanceRemindersEnabled.asStateFlow()

    private val _noticeAlertsEnabled = MutableStateFlow(true)
    val noticeAlertsEnabled: StateFlow<Boolean> = _noticeAlertsEnabled.asStateFlow()

    private val _announcementsEnabled = MutableStateFlow(true)
    val announcementsEnabled: StateFlow<Boolean> = _announcementsEnabled.asStateFlow()

    // Appearance Preference ("System", "Dark", "Light")
    private val _appearancePreference = MutableStateFlow("Dark")
    val appearancePreference: StateFlow<String> = _appearancePreference.asStateFlow()

    // Verification method for activation code
    fun verifyActivationCode(context: Context, code: String): ActivationResult {
        val trimmed = code.trim().uppercase()
        if (trimmed == "USED12" || trimmed == "USED00") {
            return ActivationResult.ALREADY_USED
        }
        if (trimmed.length != 6) {
            return ActivationResult.INVALID_CODE
        }

        val updatedStudent = Student(
            id = "24AIM001",
            rollNumber = "2024-AI-001",
            name = "Devesh Dwivedi",
            hostelName = "Charak Chatras",
            roomNumber = "214",
            floorNumber = "2",
            course = "B.Tech",
            branch = "Artificial Intelligence",
            year = "2nd Year",
            isActivated = true,
            activationCode = trimmed
        )
        _student.value = updatedStudent

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(KEY_IS_ACTIVATED, true)
            .putString(KEY_STUDENT_ID, updatedStudent.id)
            .putString(KEY_STUDENT_NAME, updatedStudent.name)
            .putString(KEY_HOSTEL_NAME, updatedStudent.hostelName)
            .putString(KEY_ROOM_NUMBER, updatedStudent.roomNumber)
            .putString(KEY_FLOOR_NUMBER, updatedStudent.floorNumber)
            .apply()

        return ActivationResult.SUCCESS
    }

    // Toggle active session for testing/demo state (Cycles between Morning Active, Evening Active, and None)
    private var demoCycleIndex = 0

    fun cycleActiveSessionState() {
        demoCycleIndex = (demoCycleIndex + 1) % 3
        _hasMarkedCurrentSession.value = false

        _activeSession.value = when (demoCycleIndex) {
            1 -> AttendanceSession(
                id = "SESS_ACTIVE_MORNING",
                sessionType = AttendanceSessionType.MORNING,
                title = "Morning Attendance",
                hostelName = "Charak Chatras",
                dateText = "2 Oct 2026",
                startTimeText = "07:00 AM",
                endTimeText = "09:00 AM",
                status = SessionStatus.ACTIVE,
                cutoffTimeText = "09:00 AM"
            )
            2 -> AttendanceSession(
                id = "SESS_ACTIVE_EVENING",
                sessionType = AttendanceSessionType.EVENING,
                title = "Evening Attendance",
                hostelName = "Charak Chatras",
                dateText = "2 Oct 2026",
                startTimeText = "08:00 PM",
                endTimeText = "10:30 PM",
                status = SessionStatus.ACTIVE,
                cutoffTimeText = "10:30 PM"
            )
            else -> null
        }
    }

    // Student marks presence
    fun markAttendance(): Boolean {
        val session = _activeSession.value ?: return false
        if (_hasMarkedCurrentSession.value) return false

        _hasMarkedCurrentSession.value = true
        val markTime = if (session.sessionType == AttendanceSessionType.MORNING) "07:42 AM" else "08:12 PM"
        val newRecord = AttendanceRecord(
            id = "REC_${System.currentTimeMillis()}",
            sessionId = session.id,
            sessionType = session.sessionType,
            title = session.title,
            dateTimeText = "${session.dateText} • $markTime",
            dateText = session.dateText,
            timeText = markTime,
            monthYearText = "October 2026",
            status = AttendanceStatus.PRESENT
        )
        _attendanceHistory.value = listOf(newRecord) + _attendanceHistory.value
        return true
    }

    // Mark notice as read
    fun markNoticeRead(noticeId: String) {
        _notices.value = _notices.value.map { notice ->
            if (notice.id == noticeId) notice.copy(isUnread = false) else notice
        }
    }

    // Settings actions
    fun setAttendanceReminders(enabled: Boolean) { _attendanceRemindersEnabled.value = enabled }
    fun setNoticeAlerts(enabled: Boolean) { _noticeAlertsEnabled.value = enabled }
    fun setAnnouncements(enabled: Boolean) { _announcementsEnabled.value = enabled }
    fun setAppearancePreference(pref: String) { _appearancePreference.value = pref }

    fun signOut(context: Context) {
        _student.value = Student(isActivated = false)
        _activeSession.value = null
        _hasMarkedCurrentSession.value = false

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}

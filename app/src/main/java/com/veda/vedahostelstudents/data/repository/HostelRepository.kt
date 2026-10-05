package com.veda.vedahostelstudents.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.veda.vedahostelstudents.data.model.AttendanceRecord
import com.veda.vedahostelstudents.data.model.AttendanceSession
import com.veda.vedahostelstudents.data.model.AttendanceSessionType
import com.veda.vedahostelstudents.data.model.AttendanceStatus
import com.veda.vedahostelstudents.data.model.ContactInfo
import com.veda.vedahostelstudents.data.model.HostelInfo
import com.veda.vedahostelstudents.data.model.MealItem
import com.veda.vedahostelstudents.data.model.MessDayMenu
import com.veda.vedahostelstudents.data.model.Notice
import com.veda.vedahostelstudents.data.model.SessionStatus
import com.veda.vedahostelstudents.data.model.Student
import com.veda.vedahostelstudents.data.util.StudentNotificationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class AttendanceScheduleConfig(
    val hostelId: String = "",
    val morningStart: String = "",
    val morningEnd: String = "",
    val eveningStart: String = "",
    val eveningEnd: String = "",
    val timezone: String = "Asia/Kolkata",
    val isConfigured: Boolean = false
)

data class NextSessionInfo(
    val sessionType: AttendanceSessionType,
    val title: String,
    val timeRangeText: String
)

object HostelRepository {

    private const val PREFS_NAME = "veda_student_prefs"
    private const val KEY_IS_ACTIVATED = "is_activated"
    private const val KEY_STUDENT_ID = "student_id"
    private const val KEY_STUDENT_NAME = "student_name"
    private const val KEY_ROLL_NUMBER = "roll_number"
    private const val KEY_HOSTEL_ID = "hostel_id"
    private const val KEY_HOSTEL_NAME = "hostel_name"
    private const val KEY_CAMPUS = "campus"
    private const val KEY_ADDRESS = "address"
    private const val KEY_ROOM_NUMBER = "room_number"
    private const val KEY_FLOOR_NUMBER = "floor_number"
    private const val KEY_COURSE = "course"
    private const val KEY_BRANCH = "branch"
    private const val KEY_YEAR = "year"
    private const val KEY_WARDEN_NAME = "warden_name"
    private const val KEY_APPEARANCE_PREFERENCE = "appearance_preference"

    // Real-Time Listeners
    private var studentListener: ListenerRegistration? = null
    private var hostelListener: ListenerRegistration? = null
    private var noticesListener: ListenerRegistration? = null
    private var contactsListener: ListenerRegistration? = null
    private var messMenuListener: ListenerRegistration? = null
    private var scheduleListener: ListenerRegistration? = null
    private var activeHostelId: String? = null
    private var activeStudentId: String? = null

    // Current Student State
    private val _student = MutableStateFlow(Student(isActivated = false))
    val student: StateFlow<Student> = _student.asStateFlow()

    // Hostel Information
    private val _hostelInfo = MutableStateFlow(HostelInfo())
    val hostelInfo: StateFlow<HostelInfo> = _hostelInfo.asStateFlow()

    private val _isHostelLoading = MutableStateFlow(false)
    val isHostelLoading: StateFlow<Boolean> = _isHostelLoading.asStateFlow()

    private val _hostelError = MutableStateFlow<String?>(null)
    val hostelError: StateFlow<String?> = _hostelError.asStateFlow()

    // Attendance Schedule Config
    private val _attendanceSchedule = MutableStateFlow(AttendanceScheduleConfig())
    val attendanceSchedule: StateFlow<AttendanceScheduleConfig> = _attendanceSchedule.asStateFlow()

    // Active Attendance Session State
    private val _activeSession = MutableStateFlow<AttendanceSession?>(null)
    val activeSession: StateFlow<AttendanceSession?> = _activeSession.asStateFlow()

    // Next Session Info
    private val _nextSessionInfo = MutableStateFlow<NextSessionInfo?>(null)
    val nextSessionInfo: StateFlow<NextSessionInfo?> = _nextSessionInfo.asStateFlow()

    // Has Marked Current Active Session
    private val _hasMarkedCurrentSession = MutableStateFlow(false)
    val hasMarkedCurrentSession: StateFlow<Boolean> = _hasMarkedCurrentSession.asStateFlow()

    // Current Active Session Attendance Record (if marked)
    private val _currentSessionRecord = MutableStateFlow<AttendanceRecord?>(null)
    val currentSessionRecord: StateFlow<AttendanceRecord?> = _currentSessionRecord.asStateFlow()

    // Recorded Attendance History
    private val _attendanceHistory = MutableStateFlow<List<AttendanceRecord>>(emptyList())
    val attendanceHistory: StateFlow<List<AttendanceRecord>> = _attendanceHistory.asStateFlow()

    private val _isHistoryLoading = MutableStateFlow(false)
    val isHistoryLoading: StateFlow<Boolean> = _isHistoryLoading.asStateFlow()

    private val _historyError = MutableStateFlow<String?>(null)
    val historyError: StateFlow<String?> = _historyError.asStateFlow()

    // Notices State
    private val _notices = MutableStateFlow<List<Notice>>(emptyList())
    val notices: StateFlow<List<Notice>> = _notices.asStateFlow()

    private val _isNoticesLoading = MutableStateFlow(false)
    val isNoticesLoading: StateFlow<Boolean> = _isNoticesLoading.asStateFlow()

    // Contacts State
    private val _contacts = MutableStateFlow<List<ContactInfo>>(emptyList())
    val contacts: StateFlow<List<ContactInfo>> = _contacts.asStateFlow()

    private val _isContactsLoading = MutableStateFlow(false)
    val isContactsLoading: StateFlow<Boolean> = _isContactsLoading.asStateFlow()

    // Mess Menu State
    private val _messMenu = MutableStateFlow(MessDayMenu(isPublished = false))
    val messMenu: StateFlow<MessDayMenu> = _messMenu.asStateFlow()

    private val _isMessLoading = MutableStateFlow(false)
    val isMessLoading: StateFlow<Boolean> = _isMessLoading.asStateFlow()

    private var appContext: Context? = null
    private var hasLoadedNotices = false

    // Canonical Notification Toggles - Delegated to StudentNotificationPreferences
    val attendanceRemindersEnabled: StateFlow<Boolean> = StudentNotificationPreferences.attendanceRemindersEnabled
    val noticeAlertsEnabled: StateFlow<Boolean> = StudentNotificationPreferences.hostelNoticesEnabled
    val announcementsEnabled: StateFlow<Boolean> = StudentNotificationPreferences.importantAnnouncementsEnabled

    // Appearance Preference ("System", "Dark", "Light")
    private val _appearancePreference = MutableStateFlow("Dark")
    val appearancePreference: StateFlow<String> = _appearancePreference.asStateFlow()

    // Initialize repository and check persisted activation state from SharedPreferences
    fun init(context: Context) {
        appContext = context.applicationContext
        StudentNotificationPreferences.init(context)
        StudentNotificationManager.createNotificationChannels(context)

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _appearancePreference.value = prefs.getString(KEY_APPEARANCE_PREFERENCE, "System") ?: "System"
        val isActivated = prefs.getBoolean(KEY_IS_ACTIVATED, false)
        if (isActivated) {
            val id = prefs.getString(KEY_STUDENT_ID, "") ?: ""
            val name = prefs.getString(KEY_STUDENT_NAME, "") ?: ""
            val roll = prefs.getString(KEY_ROLL_NUMBER, "") ?: ""
            val hostelId = prefs.getString(KEY_HOSTEL_ID, "") ?: ""
            val hostel = prefs.getString(KEY_HOSTEL_NAME, "") ?: ""
            val campus = prefs.getString(KEY_CAMPUS, "") ?: ""
            val address = prefs.getString(KEY_ADDRESS, "") ?: ""
            val room = prefs.getString(KEY_ROOM_NUMBER, "") ?: ""
            val floor = prefs.getString(KEY_FLOOR_NUMBER, "") ?: ""
            val course = prefs.getString(KEY_COURSE, "B.Tech") ?: "B.Tech"
            val branch = prefs.getString(KEY_BRANCH, "General") ?: "General"
            val year = prefs.getString(KEY_YEAR, "1st Year") ?: "1st Year"
            val warden = prefs.getString(KEY_WARDEN_NAME, "Warden") ?: "Warden"

            _student.value = Student(
                studentId = id,
                id = id,
                rollNumber = roll,
                fullName = name,
                name = name,
                hostelId = hostelId,
                hostelName = hostel,
                roomNumber = room,
                floorNumber = floor,
                course = course,
                branch = branch,
                year = year,
                wardenName = warden,
                isActivated = true
            )

            _hostelInfo.value = HostelInfo(
                hostelId = hostelId,
                name = hostel,
                campus = campus,
                address = address,
                wardenName = warden
            )

            if (hostelId.isNotBlank() && id.isNotBlank()) {
                startRealtimeListeners(context, hostelId, id)
            } else {
                StudentFcmManager.registerCurrentFcmToken(context)
                StudentFcmManager.syncNotificationPreferencesToServer(
                    attendanceReminders = attendanceRemindersEnabled.value,
                    hostelNotices = noticeAlertsEnabled.value,
                    importantAnnouncements = announcementsEnabled.value
                )
            }
        } else {
            _student.value = Student(isActivated = false)
        }
    }

    fun startRealtimeListeners(context: Context, hostelId: String, studentId: String) {
        if (hostelId.isBlank() || studentId.isBlank()) return
        if (activeHostelId == hostelId && activeStudentId == studentId && studentListener != null) return

        stopRealtimeListeners()
        activeHostelId = hostelId
        activeStudentId = studentId

        StudentFcmManager.registerCurrentFcmToken(context)
        StudentFcmManager.syncNotificationPreferencesToServer(
            attendanceReminders = attendanceRemindersEnabled.value,
            hostelNotices = noticeAlertsEnabled.value,
            importantAnnouncements = announcementsEnabled.value
        )

        val firestore = FirebaseFirestore.getInstance()

        _isHostelLoading.value = true
        _isNoticesLoading.value = true
        _isContactsLoading.value = true
        _isMessLoading.value = true

        // 1. Real-Time Student Profile Listener
        studentListener = firestore.collection("hostels")
            .document(hostelId)
            .collection("students")
            .document(studentId)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener

                val name = snapshot.getString("name") ?: snapshot.getString("fullName") ?: "Student"
                val roll = snapshot.getString("rollNumber") ?: ""
                val room = snapshot.getString("roomNumber") ?: ""
                val fl = snapshot.getString("floor") ?: ""
                val ph = snapshot.getString("phone") ?: ""
                val em = snapshot.getString("email") ?: ""
                val crs = snapshot.getString("course") ?: "B.Tech"
                val br = snapshot.getString("branch") ?: "General"
                val yr = snapshot.getString("year") ?: "1st Year"

                val updatedStudent = _student.value.copy(
                    id = studentId,
                    studentId = studentId,
                    fullName = name,
                    name = name,
                    rollNumber = roll,
                    phoneNumber = ph,
                    email = em,
                    course = crs,
                    branch = br,
                    year = yr,
                    hostelId = hostelId,
                    roomNumber = room,
                    floorNumber = fl,
                    isActivated = true
                )
                _student.value = updatedStudent
                setActivatedStudentAndHostel(context, updatedStudent, _hostelInfo.value)
            }

        // 2. Real-Time Hostel Info Listener
        hostelListener = firestore.collection("hostels")
            .document(hostelId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _hostelError.value = "Unable to load hostel details."
                    _isHostelLoading.value = false
                    return@addSnapshotListener
                }
                if (snapshot == null || !snapshot.exists()) {
                    _hostelError.value = "Hostel details not found."
                    _isHostelLoading.value = false
                    return@addSnapshotListener
                }

                val hName = snapshot.getString("name") ?: ""
                val campus = snapshot.getString("location") ?: snapshot.getString("campus") ?: snapshot.getString("address") ?: ""
                val addr = snapshot.getString("address") ?: ""
                val city = snapshot.getString("city") ?: ""
                val state = snapshot.getString("state") ?: ""
                val pin = snapshot.getString("pinCode") ?: snapshot.getString("pin") ?: ""
                val desc = snapshot.getString("description") ?: ""
                val warden = snapshot.getString("wardenName") ?: ""
                val floors = (snapshot.getLong("totalFloors") ?: 0L).toInt()
                val rooms = (snapshot.getLong("totalRooms") ?: 0L).toInt()
                val students = (snapshot.getLong("totalStudents") ?: 0L).toInt()

                val updatedHostel = HostelInfo(
                    hostelId = hostelId,
                    name = hName,
                    campus = campus,
                    address = addr,
                    city = city,
                    state = state,
                    pinCode = pin,
                    description = desc,
                    totalFloors = floors,
                    totalRooms = rooms,
                    totalStudents = students,
                    wardenName = warden,
                    contacts = _contacts.value
                )
                _hostelInfo.value = updatedHostel
                _isHostelLoading.value = false
                _hostelError.value = null

                val updatedStudent = _student.value.copy(hostelName = hName, wardenName = warden)
                _student.value = updatedStudent
                setActivatedStudentAndHostel(context, updatedStudent, updatedHostel)
            }

        // 3. Real-Time Attendance Schedule Listener
        scheduleListener = firestore.collection("hostels")
            .document(hostelId)
            .collection("attendanceSchedule")
            .document("config")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || !snapshot.exists()) {
                    val unconfiguredSched = AttendanceScheduleConfig(
                        hostelId = hostelId,
                        isConfigured = false
                    )
                    _attendanceSchedule.value = unconfiguredSched
                    recalculateActiveSessionState(unconfiguredSched)
                    return@addSnapshotListener
                }

                val mStart = snapshot.getString("morningStart") ?: ""
                val mEnd = snapshot.getString("morningEnd") ?: ""
                val eStart = snapshot.getString("eveningStart") ?: ""
                val eEnd = snapshot.getString("eveningEnd") ?: ""
                val tz = snapshot.getString("timezone") ?: "Asia/Kolkata"

                val isConfigured = mStart.isNotBlank() && mEnd.isNotBlank() &&
                        eStart.isNotBlank() && eEnd.isNotBlank()

                val sched = AttendanceScheduleConfig(
                    hostelId = hostelId,
                    morningStart = mStart,
                    morningEnd = mEnd,
                    eveningStart = eStart,
                    eveningEnd = eEnd,
                    timezone = tz,
                    isConfigured = isConfigured
                )
                _attendanceSchedule.value = sched
                recalculateActiveSessionState(sched)
                StudentAttendanceRepository.refreshAttendanceHistory()
            }

        // 4. Real-Time Notices Listener
        noticesListener = firestore.collection("hostels")
            .document(hostelId)
            .collection("notices")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    _isNoticesLoading.value = false
                    return@addSnapshotListener
                }

                val tz = TimeZone.getTimeZone("Asia/Kolkata")
                val dateOnlyFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).apply {
                    timeZone = tz
                }

                val noticeList = snapshot.documents.mapNotNull { doc ->
                    try {
                        val isActive = doc.getBoolean("isActive") ?: true
                        if (!isActive) return@mapNotNull null

                        val id = doc.id
                        val title = doc.getString("title") ?: ""
                        val desc = doc.getString("description") ?: doc.getString("message") ?: ""
                        val category = doc.getString("category") ?: "General"
                        val createdBy = doc.getString("createdByName") ?: doc.getString("createdBy") ?: "Warden Office"
                        val priority = doc.getString("priority") ?: "NORMAL"
                        val timestamp = doc.getTimestamp("createdAt")?.toDate() ?: Date()

                        Notice(
                            id = id,
                            noticeId = id,
                            title = title,
                            description = desc,
                            category = category,
                            dateText = dateOnlyFormatter.format(timestamp),
                            isUnread = true,
                            publisher = createdBy,
                            isActive = true,
                            priority = priority,
                            createdAt = timestamp
                        )
                    } catch (_: Exception) {
                        null
                    }
                }.sortedByDescending { it.createdAt?.time ?: 0L }

                _notices.value = noticeList
                _isNoticesLoading.value = false
                hasLoadedNotices = true
            }

        // 5. Real-Time Contacts Listener
        contactsListener = firestore.collection("hostels")
            .document(hostelId)
            .collection("contacts")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    _isContactsLoading.value = false
                    return@addSnapshotListener
                }

                val contactList = snapshot.documents.mapNotNull { doc ->
                    try {
                        val isActive = doc.getBoolean("isActive") ?: true
                        if (!isActive) return@mapNotNull null

                        val id = doc.id
                        val name = doc.getString("name") ?: ""
                        val role = doc.getString("role") ?: ""
                        val dept = doc.getString("department") ?: ""
                        val phone = doc.getString("phone") ?: doc.getString("phoneNumber") ?: ""
                        val email = doc.getString("email") ?: ""
                        val desc = doc.getString("description") ?: ""
                        val priority = (doc.getLong("priority") ?: 0L).toInt()
                        val title = doc.getString("title") ?: role.ifBlank { name }

                        ContactInfo(
                            contactId = id,
                            title = title,
                            name = name,
                            role = role,
                            department = dept,
                            phoneNumber = phone,
                            email = email,
                            description = desc,
                            isActive = true,
                            priority = priority
                        )
                    } catch (_: Exception) {
                        null
                    }
                }.sortedBy { it.priority }

                _contacts.value = contactList
                _hostelInfo.value = _hostelInfo.value.copy(contacts = contactList)
                _isContactsLoading.value = false
            }

        // 6. Real-Time Mess Menu Listener
        messMenuListener = firestore.collection("hostels")
            .document(hostelId)
            .collection("messMenus")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    _isMessLoading.value = false
                    return@addSnapshotListener
                }

                val tz = TimeZone.getTimeZone("Asia/Kolkata")
                val dateSdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply { timeZone = tz }
                val displayDateSdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).apply { timeZone = tz }
                val now = Date()
                val todayDateStr = dateSdf.format(now)
                val todayDisplayDateStr = displayDateSdf.format(now)

                val todayDoc = snapshot.documents.firstOrNull { doc ->
                    val d = doc.getString("date") ?: ""
                    val published = doc.getBoolean("isPublished") ?: true
                    d == todayDateStr && published
                }

                if (todayDoc != null) {
                    val menuId = todayDoc.id
                    val breakfastStr = todayDoc.getString("breakfast") ?: ""
                    val lunchStr = todayDoc.getString("lunch") ?: ""
                    val snacksStr = todayDoc.getString("snacks") ?: ""
                    val dinnerStr = todayDoc.getString("dinner") ?: ""

                    val breakfastTime = todayDoc.getString("breakfastTime") ?: "07:30 AM – 09:30 AM"
                    val lunchTime = todayDoc.getString("lunchTime") ?: "12:30 PM – 02:30 PM"
                    val snacksTime = todayDoc.getString("snacksTime") ?: "05:00 PM – 06:00 PM"
                    val dinnerTime = todayDoc.getString("dinnerTime") ?: "08:00 PM – 09:30 PM"

                    val mealsList = mutableListOf<MealItem>()

                    if (breakfastStr.isNotBlank()) {
                        val items = breakfastStr.split(",").map { it.trim() }.filter { it.isNotBlank() }
                        mealsList.add(MealItem(category = "Breakfast", timeText = breakfastTime, items = items))
                    }

                    if (lunchStr.isNotBlank()) {
                        val items = lunchStr.split(",").map { it.trim() }.filter { it.isNotBlank() }
                        mealsList.add(MealItem(category = "Lunch", timeText = lunchTime, items = items))
                    }

                    if (snacksStr.isNotBlank()) {
                        val items = snacksStr.split(",").map { it.trim() }.filter { it.isNotBlank() }
                        mealsList.add(MealItem(category = "Evening Snacks", timeText = snacksTime, items = items))
                    }

                    if (dinnerStr.isNotBlank()) {
                        val items = dinnerStr.split(",").map { it.trim() }.filter { it.isNotBlank() }
                        mealsList.add(MealItem(category = "Dinner", timeText = dinnerTime, items = items))
                    }

                    _messMenu.value = MessDayMenu(
                        menuId = menuId,
                        hostelId = hostelId,
                        date = todayDateStr,
                        dayName = "Today",
                        dateText = todayDisplayDateStr,
                        meals = mealsList,
                        isPublished = true
                    )
                } else {
                    _messMenu.value = MessDayMenu(
                        menuId = "",
                        hostelId = hostelId,
                        date = todayDateStr,
                        dayName = "Today",
                        dateText = todayDisplayDateStr,
                        meals = emptyList(),
                        isPublished = false
                    )
                }
                _isMessLoading.value = false
            }

        // 7. Real-Time Attendance History Listener for Student
        StudentAttendanceRepository.startAttendanceHistoryListener(hostelId, studentId)
    }

    fun stopRealtimeListeners() {
        studentListener?.remove()
        studentListener = null
        hostelListener?.remove()
        hostelListener = null
        noticesListener?.remove()
        noticesListener = null
        contactsListener?.remove()
        contactsListener = null
        messMenuListener?.remove()
        messMenuListener = null
        scheduleListener?.remove()
        scheduleListener = null
        activeHostelId = null
        activeStudentId = null
    }

    fun recalculateActiveSessionState(sched: AttendanceScheduleConfig) {
        _attendanceSchedule.value = sched
        if (!sched.isConfigured || sched.morningStart.isBlank() || sched.morningEnd.isBlank() ||
            sched.eveningStart.isBlank() || sched.eveningEnd.isBlank()) {
            _activeSession.value = null
            _nextSessionInfo.value = null
            _hasMarkedCurrentSession.value = false
            _currentSessionRecord.value = null
            return
        }

        val tz = TimeZone.getTimeZone(sched.timezone.ifBlank { "Asia/Kolkata" })
        val cal = Calendar.getInstance(tz)
        val currentMins = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)

        val mStart = parseTimeToMins(sched.morningStart)
        val mEnd = parseTimeToMins(sched.morningEnd)
        val eStart = parseTimeToMins(sched.eveningStart)
        val eEnd = parseTimeToMins(sched.eveningEnd)

        val sessionDateSdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply { timeZone = tz }
        val displayDateSdf = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).apply { timeZone = tz }
        val now = Date()
        val dateStrForId = sessionDateSdf.format(now)
        val dateStrForDisplay = displayDateSdf.format(now)

        val formattedMStart = formatTime12Hour(sched.morningStart)
        val formattedMEnd = formatTime12Hour(sched.morningEnd)
        val formattedEStart = formatTime12Hour(sched.eveningStart)
        val formattedEEnd = formatTime12Hour(sched.eveningEnd)

        when {
            currentMins in mStart until mEnd -> {
                val sess = AttendanceSession(
                    id = "${dateStrForId}_MORNING",
                    sessionType = AttendanceSessionType.MORNING,
                    title = "Morning Attendance",
                    hostelName = _hostelInfo.value.name,
                    dateText = dateStrForDisplay,
                    startTimeText = formattedMStart,
                    endTimeText = formattedMEnd,
                    status = SessionStatus.ACTIVE,
                    cutoffTimeText = formattedMEnd
                )
                _activeSession.value = sess
                _nextSessionInfo.value = NextSessionInfo(
                    sessionType = AttendanceSessionType.EVENING,
                    title = "Evening Attendance",
                    timeRangeText = "$formattedEStart – $formattedEEnd"
                )
                checkIfAlreadyMarked(sess.id)
            }
            currentMins in eStart until eEnd -> {
                val sess = AttendanceSession(
                    id = "${dateStrForId}_EVENING",
                    sessionType = AttendanceSessionType.EVENING,
                    title = "Evening Attendance",
                    hostelName = _hostelInfo.value.name,
                    dateText = dateStrForDisplay,
                    startTimeText = formattedEStart,
                    endTimeText = formattedEEnd,
                    status = SessionStatus.ACTIVE,
                    cutoffTimeText = formattedEEnd
                )
                _activeSession.value = sess
                _nextSessionInfo.value = NextSessionInfo(
                    sessionType = AttendanceSessionType.MORNING,
                    title = "Morning Attendance (Tomorrow)",
                    timeRangeText = "$formattedMStart – $formattedMEnd"
                )
                checkIfAlreadyMarked(sess.id)
            }
            else -> {
                _activeSession.value = null
                _hasMarkedCurrentSession.value = false
                _currentSessionRecord.value = null

                val nextInfo = if (currentMins < mStart) {
                    NextSessionInfo(
                        sessionType = AttendanceSessionType.MORNING,
                        title = "Morning Attendance",
                        timeRangeText = "$formattedMStart – $formattedMEnd"
                    )
                } else if (currentMins < eStart) {
                    NextSessionInfo(
                        sessionType = AttendanceSessionType.EVENING,
                        title = "Evening Attendance",
                        timeRangeText = "$formattedEStart – $formattedEEnd"
                    )
                } else {
                    NextSessionInfo(
                        sessionType = AttendanceSessionType.MORNING,
                        title = "Morning Attendance (Tomorrow)",
                        timeRangeText = "$formattedMStart – $formattedMEnd"
                    )
                }
                _nextSessionInfo.value = nextInfo
            }
        }
    }

    private fun checkIfAlreadyMarked(sessionId: String) {
        if (sessionId.isBlank()) {
            _hasMarkedCurrentSession.value = false
            _currentSessionRecord.value = null
            return
        }
        val record = _attendanceHistory.value.firstOrNull {
            it.sessionId == sessionId && (it.status == AttendanceStatus.PRESENT || it.status == AttendanceStatus.ABSENT)
        }
        if (record != null) {
            _hasMarkedCurrentSession.value = true
            _currentSessionRecord.value = record
        } else {
            _hasMarkedCurrentSession.value = false
            _currentSessionRecord.value = null
        }
    }

    fun setActivatedStudentAndHostel(context: Context, updatedStudent: Student, updatedHostelInfo: HostelInfo) {
        _student.value = updatedStudent
        _hostelInfo.value = updatedHostelInfo

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(KEY_IS_ACTIVATED, true)
            .putString(KEY_STUDENT_ID, updatedStudent.id)
            .putString(KEY_STUDENT_NAME, updatedStudent.name)
            .putString(KEY_ROLL_NUMBER, updatedStudent.rollNumber)
            .putString(KEY_HOSTEL_ID, updatedStudent.hostelId)
            .putString(KEY_HOSTEL_NAME, updatedStudent.hostelName)
            .putString(KEY_CAMPUS, updatedHostelInfo.campus)
            .putString(KEY_ADDRESS, updatedHostelInfo.address)
            .putString(KEY_ROOM_NUMBER, updatedStudent.roomNumber)
            .putString(KEY_FLOOR_NUMBER, updatedStudent.floorNumber)
            .putString(KEY_COURSE, updatedStudent.course)
            .putString(KEY_BRANCH, updatedStudent.branch)
            .putString(KEY_YEAR, updatedStudent.year)
            .putString(KEY_WARDEN_NAME, updatedStudent.wardenName)
            .apply()
    }

    fun setHistoryLoading(loading: Boolean) {
        _isHistoryLoading.value = loading
    }

    fun setHistoryError(error: String?) {
        _historyError.value = error
        _isHistoryLoading.value = false
    }

    fun updateAttendanceHistory(records: List<AttendanceRecord>) {
        _attendanceHistory.value = records
        _isHistoryLoading.value = false
        _historyError.value = null
        val active = _activeSession.value
        if (active != null) {
            checkIfAlreadyMarked(active.id)
        }
    }

    fun setHasMarkedCurrentSession(hasMarked: Boolean) {
        _hasMarkedCurrentSession.value = hasMarked
        val active = _activeSession.value
        if (active != null) {
            checkIfAlreadyMarked(active.id)
        }
    }

    fun markNoticeRead(noticeId: String) {
        _notices.value = _notices.value.map { notice ->
            if (notice.id == noticeId) notice.copy(isUnread = false) else notice
        }
    }

    fun setAttendanceReminders(context: Context?, enabled: Boolean) {
        StudentNotificationPreferences.setAttendanceReminders(context, enabled)
        StudentFcmManager.syncNotificationPreferencesToServer(
            attendanceReminders = enabled,
            hostelNotices = noticeAlertsEnabled.value,
            importantAnnouncements = announcementsEnabled.value
        )
    }
    fun setAttendanceReminders(enabled: Boolean) {
        setAttendanceReminders(appContext, enabled)
    }

    fun setNoticeAlerts(context: Context?, enabled: Boolean) {
        StudentNotificationPreferences.setHostelNotices(context, enabled)
        StudentFcmManager.syncNotificationPreferencesToServer(
            attendanceReminders = attendanceRemindersEnabled.value,
            hostelNotices = enabled,
            importantAnnouncements = announcementsEnabled.value
        )
    }
    fun setNoticeAlerts(enabled: Boolean) {
        setNoticeAlerts(appContext, enabled)
    }

    fun setAnnouncements(context: Context?, enabled: Boolean) {
        StudentNotificationPreferences.setImportantAnnouncements(context, enabled)
        StudentFcmManager.syncNotificationPreferencesToServer(
            attendanceReminders = attendanceRemindersEnabled.value,
            hostelNotices = noticeAlertsEnabled.value,
            importantAnnouncements = enabled
        )
    }
    fun setAnnouncements(enabled: Boolean) {
        setAnnouncements(appContext, enabled)
    }
    fun setAppearancePreference(context: Context, pref: String) {
        _appearancePreference.value = pref
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_APPEARANCE_PREFERENCE, pref).apply()
    }
    fun setAppearancePreference(pref: String) {
        _appearancePreference.value = pref
    }

    fun signOut(context: Context) {
        stopRealtimeListeners()
        _student.value = Student(isActivated = false)
        _hostelInfo.value = HostelInfo()
        _activeSession.value = null
        _nextSessionInfo.value = null
        _hasMarkedCurrentSession.value = false
        _currentSessionRecord.value = null
        _attendanceHistory.value = emptyList()
        _notices.value = emptyList()
        _contacts.value = emptyList()
        _messMenu.value = MessDayMenu(isPublished = false)

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .remove(KEY_IS_ACTIVATED)
            .remove(KEY_STUDENT_ID)
            .remove(KEY_STUDENT_NAME)
            .remove(KEY_ROLL_NUMBER)
            .remove(KEY_HOSTEL_ID)
            .remove(KEY_HOSTEL_NAME)
            .remove(KEY_CAMPUS)
            .remove(KEY_ADDRESS)
            .remove(KEY_ROOM_NUMBER)
            .remove(KEY_FLOOR_NUMBER)
            .remove(KEY_COURSE)
            .remove(KEY_BRANCH)
            .remove(KEY_YEAR)
            .remove(KEY_WARDEN_NAME)
            .apply()

        FirebaseAuth.getInstance().signOut()
    }

    private fun parseTimeToMins(timeStr: String): Int {
        val parts = timeStr.trim().split(":")
        val h = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val m = parts.getOrNull(1)?.toIntOrNull() ?: 0
        return h * 60 + m
    }

    fun formatTime12Hour(time24: String): String {
        if (time24.isBlank()) return ""
        return try {
            val parts = time24.trim().split(":")
            if (parts.size >= 2) {
                val hours = parts[0].toInt()
                val minutes = parts[1].toInt()
                val amPm = if (hours >= 12) "PM" else "AM"
                val hour12 = when (hours % 12) {
                    0 -> 12
                    else -> hours % 12
                }
                String.format(Locale.US, "%02d:%02d %s", hour12, minutes, amPm)
            } else {
                time24
            }
        } catch (e: Exception) {
            time24
        }
    }
}

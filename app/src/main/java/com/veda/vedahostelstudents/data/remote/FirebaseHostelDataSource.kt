package com.veda.vedahostelstudents.data.remote

import android.util.Log
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

class FirebaseHostelDataSource {

    private val firestore: FirebaseFirestore by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            Log.e("FirebaseHostelDS", "FirebaseFirestore initialization error: ${e.localizedMessage}")
            throw e
        }
    }

    private var studentListener: ListenerRegistration? = null
    private var activeSessionListener: ListenerRegistration? = null
    private var attendanceHistoryListener: ListenerRegistration? = null
    private var noticesListener: ListenerRegistration? = null
    private var messMenuListener: ListenerRegistration? = null

    fun observeStudent(studentId: String, onUpdate: (Student?) -> Unit) {
        try {
            studentListener?.remove()
            studentListener = firestore.collection("students")
                .document(studentId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null || !snapshot.exists()) {
                        Log.d("FirebaseHostelDS", "Student doc error or missing: ${error?.message}")
                        onUpdate(null)
                        return@addSnapshotListener
                    }

                    val student = Student(
                        studentId = snapshot.getString("studentId") ?: snapshot.id,
                        fullName = snapshot.getString("fullName") ?: snapshot.getString("name") ?: "Devesh Dwivedi",
                        rollNumber = snapshot.getString("rollNumber") ?: "2024-AI-001",
                        email = snapshot.getString("email") ?: "",
                        course = snapshot.getString("course") ?: "B.Tech",
                        branch = snapshot.getString("branch") ?: "Artificial Intelligence",
                        year = snapshot.getString("year") ?: "2nd Year",
                        hostelId = snapshot.getString("hostelId") ?: "HOSTEL_CHARAK",
                        hostelName = snapshot.getString("hostelName") ?: "Charak Chatras",
                        roomNumber = snapshot.getString("roomNumber") ?: "214",
                        floorNumber = snapshot.getString("floorNumber") ?: "2",
                        wardenName = snapshot.getString("wardenName") ?: "Dr. S. Mishra",
                        wardenPhone = snapshot.getString("wardenPhone") ?: "+91 94567 89012",
                        isActivated = snapshot.getBoolean("isActivated") ?: true
                    )
                    onUpdate(student)
                }
        } catch (e: Exception) {
            Log.e("FirebaseHostelDS", "observeStudent exception: ${e.localizedMessage}")
            onUpdate(null)
        }
    }

    fun observeActiveSession(hostelId: String, onUpdate: (AttendanceSession?) -> Unit) {
        try {
            activeSessionListener?.remove()
            activeSessionListener = firestore.collection("attendanceSessions")
                .whereEqualTo("hostelId", hostelId)
                .whereEqualTo("status", "ACTIVE")
                .addSnapshotListener { snapshots, error ->
                    if (error != null || snapshots == null || snapshots.isEmpty) {
                        onUpdate(null)
                        return@addSnapshotListener
                    }

                    val doc = snapshots.documents.first()
                    val sessionTypeStr = doc.getString("sessionType") ?: "EVENING"
                    val sessionType = try {
                        AttendanceSessionType.valueOf(sessionTypeStr)
                    } catch (e: Exception) {
                        AttendanceSessionType.EVENING
                    }

                    val session = AttendanceSession(
                        id = doc.id,
                        sessionType = sessionType,
                        title = doc.getString("title") ?: sessionType.displayName,
                        hostelName = doc.getString("hostelName") ?: "Charak Chatras",
                        dateText = doc.getString("dateText") ?: "2 Oct 2026",
                        startTimeText = doc.getString("startTimeText") ?: "08:00 PM",
                        endTimeText = doc.getString("endTimeText") ?: "10:30 PM",
                        status = SessionStatus.ACTIVE,
                        cutoffTimeText = doc.getString("cutoffTimeText") ?: "10:30 PM"
                    )
                    onUpdate(session)
                }
        } catch (e: Exception) {
            Log.e("FirebaseHostelDS", "observeActiveSession exception: ${e.localizedMessage}")
            onUpdate(null)
        }
    }

    fun observeAttendanceHistory(studentId: String, onUpdate: (List<AttendanceRecord>) -> Unit) {
        try {
            attendanceHistoryListener?.remove()
            attendanceHistoryListener = firestore.collection("attendanceRecords")
                .whereEqualTo("studentId", studentId)
                .orderBy("dateTimeText", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null || snapshots == null) {
                        onUpdate(emptyList())
                        return@addSnapshotListener
                    }

                    val records = snapshots.documents.map { doc ->
                        val sessionTypeStr = doc.getString("sessionType") ?: "EVENING"
                        val sessionType = try {
                            AttendanceSessionType.valueOf(sessionTypeStr)
                        } catch (e: Exception) {
                            AttendanceSessionType.EVENING
                        }
                        val statusStr = doc.getString("status") ?: "PRESENT"
                        val status = try {
                            AttendanceStatus.valueOf(statusStr)
                        } catch (e: Exception) {
                            AttendanceStatus.PRESENT
                        }

                        AttendanceRecord(
                            id = doc.id,
                            sessionId = doc.getString("sessionId") ?: "",
                            sessionType = sessionType,
                            title = doc.getString("title") ?: sessionType.displayName,
                            dateTimeText = doc.getString("dateTimeText") ?: "Today",
                            dateText = doc.getString("dateText") ?: "Today",
                            timeText = doc.getString("timeText") ?: "08:00 PM",
                            monthYearText = doc.getString("monthYearText") ?: "October 2026",
                            status = status
                        )
                    }
                    onUpdate(records)
                }
        } catch (e: Exception) {
            Log.e("FirebaseHostelDS", "observeAttendanceHistory exception: ${e.localizedMessage}")
            onUpdate(emptyList())
        }
    }

    fun observeNotices(hostelId: String, onUpdate: (List<Notice>) -> Unit) {
        try {
            noticesListener?.remove()
            noticesListener = firestore.collection("notices")
                .addSnapshotListener { snapshots, error ->
                    if (error != null || snapshots == null) {
                        onUpdate(emptyList())
                        return@addSnapshotListener
                    }

                    val list = snapshots.documents.mapNotNull { doc ->
                        val targetType = doc.getString("targetType") ?: "ALL_STUDENTS"
                        val targetHostelId = doc.getString("targetHostelId") ?: ""

                        if (targetType == "ALL_STUDENTS" || targetHostelId == hostelId || targetHostelId.isEmpty()) {
                            Notice(
                                id = doc.id,
                                title = doc.getString("title") ?: "Notice",
                                description = doc.getString("description") ?: "",
                                category = doc.getString("category") ?: "General",
                                dateText = doc.getString("dateText") ?: "2 Oct 2026",
                                isUnread = doc.getBoolean("isUnread") ?: false
                            )
                        } else {
                            null
                        }
                    }
                    onUpdate(list)
                }
        } catch (e: Exception) {
            Log.e("FirebaseHostelDS", "observeNotices exception: ${e.localizedMessage}")
            onUpdate(emptyList())
        }
    }

    fun markAttendance(
        studentId: String,
        session: AttendanceSession,
        onResult: (Boolean) -> Unit
    ) {
        try {
            val recordMap = mapOf(
                "sessionId" to session.id,
                "studentId" to studentId,
                "sessionType" to session.sessionType.name,
                "title" to session.title,
                "dateTimeText" to "${session.dateText} • ${session.startTimeText}",
                "dateText" to session.dateText,
                "timeText" to session.startTimeText,
                "monthYearText" to "October 2026",
                "status" to "PRESENT"
            )

            firestore.collection("attendanceRecords")
                .add(recordMap)
                .addOnSuccessListener { onResult(true) }
                .addOnFailureListener { onResult(false) }
        } catch (e: Exception) {
            Log.e("FirebaseHostelDS", "markAttendance exception: ${e.localizedMessage}")
            onResult(false)
        }
    }

    fun cleanup() {
        studentListener?.remove()
        activeSessionListener?.remove()
        attendanceHistoryListener?.remove()
        noticesListener?.remove()
        messMenuListener?.remove()
    }
}

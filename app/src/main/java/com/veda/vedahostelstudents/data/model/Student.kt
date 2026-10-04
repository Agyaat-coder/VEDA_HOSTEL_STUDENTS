package com.veda.vedahostelstudents.data.model

data class Student(
    val studentId: String = "24AIM001",
    val id: String = studentId,
    val uid: String = "",
    val fullName: String = "Devesh Dwivedi",
    val name: String = fullName,
    val rollNumber: String = "2024-AI-001",
    val email: String = "devesh.dwivedi@veda.edu.in",
    val phoneNumber: String = "+91 98765 43210",
    val course: String = "B.Tech",
    val branch: String = "Artificial Intelligence",
    val year: String = "2nd Year",
    val hostelId: String = "HOSTEL_CHARAK",
    val hostelName: String = "Charak Chatras",
    val hostelCode: String = "CHARAK_2026",
    val roomId: String = "ROOM_214",
    val roomNumber: String = "214",
    val floorNumber: String = "2",
    val wardenId: String = "WARDEN_DR_MISHRA",
    val wardenName: String = "Dr. S. Mishra",
    val wardenPhone: String = "+91 94567 89012",
    val isActivated: Boolean = false,
    val activationCode: String = "VD1234"
)

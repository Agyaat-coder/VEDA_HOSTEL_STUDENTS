package com.veda.vedahostelstudents.data.model

data class Student(
    val studentId: String = "",
    val id: String = studentId,
    val uid: String = "",
    val fullName: String = "",
    val name: String = fullName,
    val rollNumber: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val course: String = "B.Tech",
    val branch: String = "General",
    val year: String = "1st Year",
    val hostelId: String = "",
    val hostelName: String = "",
    val hostelCode: String = "",
    val roomId: String = "",
    val roomNumber: String = "",
    val floorNumber: String = "",
    val wardenId: String = "",
    val wardenName: String = "",
    val wardenPhone: String = "",
    val isActivated: Boolean = false,
    val activationCode: String = ""
)

package com.veda.vedahostelstudents.data.model

data class ContactInfo(
    val contactId: String = "",
    val title: String = "",
    val name: String = "",
    val role: String = "",
    val department: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val description: String = "",
    val isActive: Boolean = true,
    val priority: Int = 0
)

data class HostelInfo(
    val hostelId: String = "",
    val name: String = "",
    val campus: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val pinCode: String = "",
    val description: String = "",
    val totalFloors: Int = 0,
    val totalRooms: Int = 0,
    val totalStudents: Int = 0,
    val wardenName: String = "",
    val contacts: List<ContactInfo> = emptyList()
)

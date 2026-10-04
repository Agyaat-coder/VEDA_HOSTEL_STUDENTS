package com.veda.vedahostelstudents.data.model

data class ContactInfo(
    val title: String,
    val name: String,
    val phoneNumber: String,
    val role: String
)

data class HostelInfo(
    val name: String = "Charak Chatras",
    val campus: String = "VBSPU, Jaunpur",
    val address: String = "Campus Road, VBSPU University, Jaunpur, UP",
    val totalFloors: Int = 4,
    val totalRooms: Int = 120,
    val wardenName: String = "Dr. S. Mishra",
    val contacts: List<ContactInfo> = listOf(
        ContactInfo("Warden Office", "Dr. S. Mishra", "+91 94567 89012", "Chief Warden"),
        ContactInfo("Hostel Security Desk", "Ram Singh", "+91 98765 43210", "Security In-Charge"),
        ContactInfo("Mess Supervisor", "Mr. R. K. Sharma", "+91 91234 56789", "Mess Manager")
    )
)

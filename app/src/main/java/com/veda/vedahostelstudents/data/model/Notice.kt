package com.veda.vedahostelstudents.data.model

data class Notice(
    val id: String,
    val title: String,
    val description: String,
    val category: String = "General", // "Maintenance", "Mess", "Announcement"
    val dateText: String,
    val isUnread: Boolean = false,
    val publisher: String = "Warden Office"
)

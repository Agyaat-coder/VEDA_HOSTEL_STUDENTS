package com.veda.vedahostelstudents.data.model

import java.util.Date

data class Notice(
    val id: String = "",
    val noticeId: String = id,
    val title: String = "",
    val description: String = "",
    val category: String = "General", // "Maintenance", "Mess", "Announcement", "General"
    val dateText: String = "Today",
    val isUnread: Boolean = false,
    val publisher: String = "Warden Office",
    val isActive: Boolean = true,
    val priority: String = "NORMAL",
    val createdAt: Date? = null
)

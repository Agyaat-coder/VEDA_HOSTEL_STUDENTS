package com.veda.vedahostelstudents.data.model

data class MealItem(
    val category: String = "", // "Breakfast", "Lunch", "Evening Snacks", "Dinner"
    val timeText: String = "",
    val items: List<String> = emptyList(),
    val specialItem: String? = null
)

data class MessDayMenu(
    val menuId: String = "",
    val hostelId: String = "",
    val date: String = "", // "yyyy-MM-dd" in Asia/Kolkata
    val dayName: String = "Today",
    val dateText: String = "Today",
    val meals: List<MealItem> = emptyList(),
    val isPublished: Boolean = true
)

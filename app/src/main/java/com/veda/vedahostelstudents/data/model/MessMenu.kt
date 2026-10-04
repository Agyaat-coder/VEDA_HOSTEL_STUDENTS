package com.veda.vedahostelstudents.data.model

data class MealItem(
    val category: String, // "Breakfast", "Lunch", "Snacks", "Dinner"
    val timeText: String,
    val items: List<String>,
    val specialItem: String? = null
)

data class MessDayMenu(
    val dayName: String = "Today",
    val dateText: String = "2 Oct 2026",
    val meals: List<MealItem> = emptyList()
)

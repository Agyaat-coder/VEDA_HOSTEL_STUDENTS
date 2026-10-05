package com.veda.vedahostelstudents.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.MealItem
import com.veda.vedahostelstudents.data.model.MessDayMenu
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary
import com.veda.vedahostelstudents.ui.theme.VedaWarningYellow

@Composable
fun MessMenuScreen(
    messMenu: MessDayMenu,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .padding(20.dp)
    ) {
        // Top Nav Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = VedaTextPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Mess Menu",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = VedaTextPrimary
                )
                Text(
                    text = messMenu.dateText.ifBlank { "Today" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = VedaBrightBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!messMenu.isPublished || messMenu.meals.isEmpty()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = VedaDarkSurface
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(VedaBrightBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Restaurant,
                            contentDescription = "No Menu",
                            tint = VedaBrightBlue,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Today's menu is not available",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VedaTextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Check back later for the updated mess menu.",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = VedaTextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // Meal Cards List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(messMenu.meals) { meal ->
                    MealCard(meal = meal)
                }
            }
        }
    }
}

@Composable
private fun MealCard(
    meal: MealItem
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = VedaDarkSurface
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(VedaBrightBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Restaurant,
                            contentDescription = meal.category,
                            tint = VedaBrightBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = meal.category,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = VedaTextPrimary
                        )
                        if (meal.timeText.isNotBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.AccessTime,
                                    contentDescription = "Time",
                                    tint = VedaTextMuted,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = meal.timeText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = VedaTextMuted
                                )
                            }
                        }
                    }
                }

                if (meal.specialItem != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(VedaWarningYellow.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Special",
                                tint = VedaWarningYellow,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = meal.specialItem,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = VedaWarningYellow
                            )
                        }
                    }
                }
            }

            if (meal.items.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))

                // Meal Items List
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    meal.items.forEach { dish ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(VedaBrightBlue)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = dish,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = VedaTextPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

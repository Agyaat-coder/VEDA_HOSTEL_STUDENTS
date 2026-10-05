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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.data.model.MealItem
import com.veda.vedahostelstudents.data.model.MessDayMenu
import com.veda.vedahostelstudents.ui.components.SegmentedControl
import com.veda.vedahostelstudents.ui.components.VedaCard
import com.veda.vedahostelstudents.ui.components.VedaEmptyState
import com.veda.vedahostelstudents.ui.components.VedaIconButton
import com.veda.vedahostelstudents.ui.theme.VedaAccentSoft
import com.veda.vedahostelstudents.ui.theme.VedaBorder
import com.veda.vedahostelstudents.ui.theme.VedaCanvas
import com.veda.vedahostelstudents.ui.theme.VedaInk
import com.veda.vedahostelstudents.ui.theme.VedaMuted
import com.veda.vedahostelstudents.ui.theme.VedaPrimary
import com.veda.vedahostelstudents.ui.theme.VedaShapesInstance
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaSurface
import com.veda.vedahostelstudents.ui.theme.VedaTheme
import com.veda.vedahostelstudents.ui.theme.VedaWarning
import com.veda.vedahostelstudents.ui.theme.VedaWarningSoft

@Composable
fun MessMenuScreen(
    messMenu: MessDayMenu,
    onBack: () -> Unit
) {
    val dayOptions = remember { listOf("Yesterday", "Today", "Tomorrow") }
    var selectedDayIndex by remember { mutableIntStateOf(1) } // "Today" by default

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaCanvas)
            .padding(horizontal = VedaSpacingInstance.screenPaddingHorizontal)
    ) {
        Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

        // Top Nav Bar Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = VedaSpacingInstance.sm)
        ) {
            VedaIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                onClick = onBack,
                tint = VedaInk
            )
            Spacer(modifier = Modifier.width(VedaSpacingInstance.xs))
            Column {
                Text(
                    text = "Today's Menu",
                    style = VedaTheme.typography.screenTitle,
                    color = VedaInk
                )
                Text(
                    text = messMenu.dateText.ifBlank { "05 October" },
                    style = VedaTheme.typography.caption,
                    color = VedaMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        // Yesterday / Today / Tomorrow Day Selector (Pages 26 & 32)
        SegmentedControl(
            items = dayOptions,
            selectedIndex = selectedDayIndex,
            onSegmentSelected = { selectedDayIndex = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.lg))

        if (!messMenu.isPublished || messMenu.meals.isEmpty()) {
            VedaEmptyState(
                title = "Today's menu is not available",
                message = "Check back later for the updated mess menu.",
                icon = Icons.Filled.Restaurant
            )
        } else {
            // Meal Cards Timeline List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.listSpacing)
            ) {
                items(messMenu.meals) { meal ->
                    MealCard(meal = meal)
                }

                // SPECIAL NOTE CARD (Pages 26 & 32)
                item {
                    VedaCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = VedaSurface,
                        borderColor = VedaBorder,
                        shape = VedaShapesInstance.large,
                        contentPadding = VedaSpacingInstance.cardPadding
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "SPECIAL NOTE",
                                style = VedaTheme.typography.caption,
                                color = VedaPrimary
                            )
                            Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))
                            Text(
                                text = "Please bring your own water bottle. Menu may change with ingredient availability.",
                                style = VedaTheme.typography.bodySecondary,
                                color = VedaMuted
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(VedaSpacingInstance.xxl))
                }
            }
        }
    }
}

@Composable
private fun MealCard(
    meal: MealItem
) {
    VedaCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = VedaSurface,
        borderColor = VedaBorder,
        shape = VedaShapesInstance.large,
        contentPadding = VedaSpacingInstance.cardPadding
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(VedaShapesInstance.small)
                            .background(VedaAccentSoft),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Restaurant,
                            contentDescription = meal.category,
                            tint = VedaPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(VedaSpacingInstance.sm))
                    Column {
                        Text(
                            text = meal.category.uppercase(),
                            style = VedaTheme.typography.caption,
                            color = VedaPrimary
                        )
                        if (meal.timeText.isNotBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.AccessTime,
                                    contentDescription = "Time",
                                    tint = VedaMuted,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = meal.timeText,
                                    style = VedaTheme.typography.caption,
                                    color = VedaMuted
                                )
                            }
                        }
                    }
                }

                if (meal.specialItem != null) {
                    Box(
                        modifier = Modifier
                            .clip(VedaShapesInstance.chip)
                            .background(VedaWarningSoft)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Special",
                                tint = VedaWarning,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = meal.specialItem,
                                style = VedaTheme.typography.caption,
                                color = VedaWarning
                            )
                        }
                    }
                }
            }

            if (meal.items.isNotEmpty()) {
                Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

                // Meal Items List
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    meal.items.forEach { dish ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(VedaPrimary)
                            )
                            Spacer(modifier = Modifier.width(VedaSpacingInstance.sm))
                            Text(
                                text = dish,
                                style = VedaTheme.typography.body,
                                fontWeight = FontWeight.Bold,
                                color = VedaInk
                            )
                        }
                    }
                }
            }
        }
    }
}

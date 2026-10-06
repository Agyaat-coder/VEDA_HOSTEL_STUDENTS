package com.veda.vedahostelstudents.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CorporateFare
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.ui.theme.VedaAccentSoft
import com.veda.vedahostelstudents.ui.theme.VedaBorder
import com.veda.vedahostelstudents.ui.theme.VedaNavBg
import com.veda.vedahostelstudents.ui.theme.VedaNavSelected
import com.veda.vedahostelstudents.ui.theme.VedaNavUnselected
import com.veda.vedahostelstudents.ui.theme.VedaShapesInstance
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaSurface
import com.veda.vedahostelstudents.ui.theme.VedaTheme

enum class BottomTab(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    TODAY("today", "TODAY", Icons.Filled.CalendarToday, Icons.Outlined.CalendarToday),
    ACTIVITY("activity", "ACTIVITY", Icons.Filled.Timeline, Icons.Outlined.Timeline),
    HOSTEL("hostel", "HOSTEL", Icons.Filled.CorporateFare, Icons.Outlined.CorporateFare),
    ME("me", "ME", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun VedaBottomBar(
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    // 72dp Floating bar with outer inset (Page 38 & 46)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = VedaSpacingInstance.lg,
                vertical = VedaSpacingInstance.sm
            )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = VedaShapesInstance.card, // 24dp rounded surface
            color = VedaSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, VedaBorder),
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = VedaSpacingInstance.sm),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomTab.entries.forEach { tab ->
                    val isSelected = currentTab == tab
                    val containerColor by animateColorAsState(
                        targetValue = if (isSelected) VedaAccentSoft else VedaSurface,
                        label = "bottomBarContainerColor"
                    )
                    val contentColor by animateColorAsState(
                        targetValue = if (isSelected) VedaNavSelected else VedaNavUnselected,
                        label = "bottomBarContentColor"
                    )

                    Box(
                        modifier = Modifier
                            .defaultMinSize(minHeight = 52.dp, minWidth = 64.dp)
                            .clip(VedaShapesInstance.medium)
                            .background(containerColor)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onTabSelected(tab) }
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.label,
                                tint = contentColor,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = tab.label,
                                style = VedaTheme.typography.navigation,
                                color = contentColor
                            )
                        }
                    }
                }
            }
        }
    }
}

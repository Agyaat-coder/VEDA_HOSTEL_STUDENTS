package com.veda.vedahostelstudents.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsSystemDaydream
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.ui.components.VedaCard
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

@Composable
fun AppearanceScreen(
    currentPreference: String,
    onSelectPreference: (String) -> Unit,
    onBack: () -> Unit
) {
    val options = remember {
        listOf(
            Triple("System", "Follows device appearance", Icons.Filled.SettingsSystemDaydream),
            Triple("Light", "Clean Daylight theme", Icons.Filled.LightMode),
            Triple("Dark", "Gentle Midnight theme", Icons.Filled.DarkMode)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaCanvas)
            .verticalScroll(rememberScrollState())
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
            Text(
                text = "Appearance",
                style = VedaTheme.typography.screenTitle,
                color = VedaInk
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        Text(
            text = "Make it feel like you.",
            style = VedaTheme.typography.sectionTitle,
            color = VedaInk
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

        Text(
            text = "Choose a comfortable look for your campus day. Your preference is saved on this device.",
            style = VedaTheme.typography.bodySecondary,
            color = VedaMuted
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // THREE VISUAL THEME PREVIEWS (PAGES 24 & 31)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(VedaSpacingInstance.sm)
        ) {
            options.forEach { (optionKey, _, icon) ->
                val isSelected = currentPreference.equals(optionKey, ignoreCase = true)
                VedaCard(
                    onClick = { onSelectPreference(optionKey) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = if (isSelected) VedaAccentSoft else VedaSurface,
                    borderColor = if (isSelected) VedaPrimary else VedaBorder,
                    shape = VedaShapesInstance.large,
                    contentPadding = VedaSpacingInstance.cardPadding
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) VedaPrimary else VedaAccentSoft),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = optionKey,
                                tint = if (isSelected) VedaSurface else VedaPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))

                        Text(
                            text = optionKey.uppercase(),
                            style = VedaTheme.typography.caption,
                            color = if (isSelected) VedaPrimary else VedaInk
                        )

                        if (isSelected) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Selected",
                                tint = VedaPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // "A QUIETER EVENING" EXPLANATORY CARD (PAGES 24 & 31)
        VedaCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = VedaSurface,
            borderColor = VedaBorder,
            shape = VedaShapesInstance.large,
            contentPadding = VedaSpacingInstance.cardPadding
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "A QUIETER EVENING",
                    style = VedaTheme.typography.caption,
                    color = VedaPrimary
                )

                Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

                Text(
                    text = "System follows your Android appearance. Dark uses gentle midnight tones, designed for comfortable evening check-ins.",
                    style = VedaTheme.typography.bodySecondary,
                    color = VedaMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xxl))
    }
}

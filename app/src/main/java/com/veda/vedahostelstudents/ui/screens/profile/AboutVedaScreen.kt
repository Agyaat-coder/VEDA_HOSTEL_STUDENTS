package com.veda.vedahostelstudents.ui.screens.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun AboutVedaScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaCanvas)
            .padding(horizontal = VedaSpacingInstance.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
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
                text = "About VEDA",
                style = VedaTheme.typography.screenTitle,
                fontWeight = FontWeight.Bold,
                color = VedaInk
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // VEDA Emblem
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(VedaAccentSoft),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Shield,
                contentDescription = "VEDA Logo",
                tint = VedaPrimary,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        Text(
            text = "VEDA Hostel Students",
            style = VedaTheme.typography.screenTitle,
            fontWeight = FontWeight.Bold,
            color = VedaInk
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Version 1.0.0",
            style = VedaTheme.typography.bodySecondary,
            fontWeight = FontWeight.Medium,
            color = VedaPrimary
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        VedaCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = VedaSurface,
            borderColor = VedaBorder,
            shape = VedaShapesInstance.large,
            contentPadding = VedaSpacingInstance.cardPadding
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your Hostel • Your Everyday Companion",
                    style = VedaTheme.typography.body,
                    fontWeight = FontWeight.Bold,
                    color = VedaInk,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))
                Text(
                    text = "VEDA Hostel Students is the official student-side companion application designed to streamline daily hostel attendance, warden broadcasts, notices, mess menus, and hostel information.",
                    style = VedaTheme.typography.bodySecondary,
                    color = VedaMuted,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

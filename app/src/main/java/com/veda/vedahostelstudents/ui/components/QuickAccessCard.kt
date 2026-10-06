package com.veda.vedahostelstudents.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.ui.theme.VedaAccentSoft
import com.veda.vedahostelstudents.ui.theme.VedaBorder
import com.veda.vedahostelstudents.ui.theme.VedaInk
import com.veda.vedahostelstudents.ui.theme.VedaMuted
import com.veda.vedahostelstudents.ui.theme.VedaPrimary
import com.veda.vedahostelstudents.ui.theme.VedaShapesInstance
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaSurface
import com.veda.vedahostelstudents.ui.theme.VedaTheme

@Composable
fun QuickAccessCard(
    title: String,
    badgeText: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    VedaCard(
        onClick = onClick,
        modifier = modifier,
        backgroundColor = VedaSurface,
        borderColor = VedaBorder,
        shape = VedaShapesInstance.medium,
        contentPadding = 12.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(VedaShapesInstance.small)
                    .background(VedaAccentSoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = VedaPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = VedaTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                color = VedaInk,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = badgeText,
                style = VedaTheme.typography.caption,
                color = VedaMuted,
                maxLines = 1
            )
        }
    }
}

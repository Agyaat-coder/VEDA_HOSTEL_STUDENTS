package com.veda.vedahostelstudents.ui.screens.profile

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
fun HelpFeedbackScreen(
    wardenPhone: String,
    onReportProblemClick: () -> Unit = {},
    onSendFeedbackClick: () -> Unit = {},
    onBack: () -> Unit
) {
    val context = LocalContext.current

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
                text = "Help & Feedback",
                style = VedaTheme.typography.screenTitle,
                color = VedaInk
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

        Text(
            text = "A better hostel starts with a conversation.",
            style = VedaTheme.typography.sectionTitle,
            color = VedaInk
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

        Text(
            text = "We're here to help you feel at home.",
            style = VedaTheme.typography.bodySecondary,
            color = VedaMuted
        )

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // TWO PRIMARY CARDS: REPORT A PROBLEM & SEND FEEDBACK (PAGES 24 & 31)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(VedaSpacingInstance.lg)
        ) {
            // REPORT A PROBLEM CARD
            VedaCard(
                onClick = onReportProblemClick,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = VedaSurface,
                borderColor = VedaBorder,
                shape = VedaShapesInstance.large,
                contentPadding = VedaSpacingInstance.cardPadding
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(VedaShapesInstance.medium)
                                .background(VedaAccentSoft),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.BugReport,
                                contentDescription = "Report Problem",
                                tint = VedaPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(VedaSpacingInstance.md))

                        Column {
                            Text(
                                text = "REPORT A PROBLEM",
                                style = VedaTheme.typography.caption,
                                color = VedaPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Something in the hostel needs attention?",
                                style = VedaTheme.typography.body,
                                fontWeight = FontWeight.Bold,
                                color = VedaInk
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Report Problem",
                                style = VedaTheme.typography.caption,
                                color = VedaPrimary
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Open",
                        tint = VedaMuted
                    )
                }
            }

            // SEND FEEDBACK CARD
            VedaCard(
                onClick = onSendFeedbackClick,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = VedaSurface,
                borderColor = VedaBorder,
                shape = VedaShapesInstance.large,
                contentPadding = VedaSpacingInstance.cardPadding
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(VedaShapesInstance.medium)
                                .background(VedaAccentSoft),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Help,
                                contentDescription = "Send Feedback",
                                tint = VedaPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(VedaSpacingInstance.md))

                        Column {
                            Text(
                                text = "SEND FEEDBACK",
                                style = VedaTheme.typography.caption,
                                color = VedaPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Tell us what you think about VEDA.",
                                style = VedaTheme.typography.body,
                                fontWeight = FontWeight.Bold,
                                color = VedaInk
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Feedback",
                                style = VedaTheme.typography.caption,
                                color = VedaPrimary
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Open",
                        tint = VedaMuted
                    )
                }
            }

            // CONTACT WARDEN DIRECT CARD
            if (wardenPhone.isNotBlank()) {
                VedaCard(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$wardenPhone")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = VedaSurface,
                    borderColor = VedaBorder,
                    shape = VedaShapesInstance.large,
                    contentPadding = VedaSpacingInstance.cardPadding
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(VedaShapesInstance.medium)
                                    .background(VedaAccentSoft),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Call,
                                    contentDescription = "Call Warden",
                                    tint = VedaPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(VedaSpacingInstance.md))

                            Column {
                                Text(
                                    text = "WARDEN OFFICE",
                                    style = VedaTheme.typography.caption,
                                    color = VedaPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Call Warden Office ($wardenPhone)",
                                    style = VedaTheme.typography.body,
                                    fontWeight = FontWeight.Bold,
                                    color = VedaInk
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = "Call",
                            tint = VedaMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

        // Supporting Footnote: "For urgent help, call hostel security through Contacts."
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = VedaSpacingInstance.xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = VedaMuted,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "For urgent help, call hostel security through Contacts.",
                style = VedaTheme.typography.caption,
                color = VedaMuted
            )
        }

        Spacer(modifier = Modifier.height(VedaSpacingInstance.xxl))
    }
}

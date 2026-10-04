package com.veda.vedahostelstudents.ui.screens.attendance

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.model.AttendanceStatus
import com.veda.vedahostelstudents.ui.components.StatusChip
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaDarkSurface
import com.veda.vedahostelstudents.ui.theme.VedaSuccessGreen
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun AttendanceSuccessScreen(
    sessionTitle: String = "Morning Attendance",
    dateTimeText: String = "2 Oct 2026 • 07:42 AM",
    onViewInActivity: () -> Unit,
    onBackToHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Green Circle Checkmark
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(VedaSuccessGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Success",
                        tint = VedaTextPrimary,
                        modifier = Modifier.size(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Attendance Recorded!",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = VedaTextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your presence has been marked\nfor today's session.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = VedaTextMuted,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(36.dp))

                // Summary Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = VedaDarkSurface
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(VedaBrightBlue.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.WbSunny,
                                    contentDescription = "Session",
                                    tint = VedaBrightBlue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = sessionTitle,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VedaTextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = dateTimeText,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = VedaTextMuted
                                )
                            }
                        }

                        StatusChip(status = AttendanceStatus.PRESENT)
                    }
                }
            }

            // Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onViewInActivity,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VedaBrightBlue,
                        contentColor = VedaTextPrimary
                    )
                ) {
                    Text(
                        text = "View in Activity",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = onBackToHome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = VedaTextPrimary
                    )
                ) {
                    Text(
                        text = "Back to Home",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

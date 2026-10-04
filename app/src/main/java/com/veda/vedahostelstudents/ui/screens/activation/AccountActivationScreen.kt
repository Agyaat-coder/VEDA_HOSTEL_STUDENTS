package com.veda.vedahostelstudents.ui.screens.activation

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.data.repository.ActivationResult
import com.veda.vedahostelstudents.ui.components.CodeInputField
import com.veda.vedahostelstudents.ui.theme.VedaAlertRed
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaDarkBackground
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun AccountActivationScreen(
    onBack: () -> Unit,
    onActivateSuccess: () -> Unit,
    onVerifyCode: (String) -> ActivationResult
) {
    var codeState by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaDarkBackground)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Top Navigation Bar
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
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Key Icon Header
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(VedaBrightBlue.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Key,
                        contentDescription = "Activation",
                        tint = VedaBrightBlue,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Activate your account",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = VedaTextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Enter the activation code provided by your warden to activate your student account.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = VedaTextMuted,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(36.dp))

                // Activation Code Box Input
                CodeInputField(
                    code = codeState,
                    onCodeChange = { input ->
                        codeState = input
                        errorMessage = null
                    }
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = errorMessage!!,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = VedaAlertRed
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Warden activation code e.g. VD1234",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = VedaTextMuted.copy(alpha = 0.8f)
                )
            }

            // Continue Button
            Button(
                onClick = {
                    when (onVerifyCode(codeState)) {
                        ActivationResult.SUCCESS -> onActivateSuccess()
                        ActivationResult.ALREADY_USED -> {
                            errorMessage = "This activation code has already been used."
                        }
                        ActivationResult.INVALID_CODE -> {
                            errorMessage = "Invalid activation code."
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = codeState.length == 6,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VedaBrightBlue,
                    contentColor = VedaTextPrimary,
                    disabledContainerColor = VedaBrightBlue.copy(alpha = 0.4f),
                    disabledContentColor = VedaTextMuted
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Continue",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

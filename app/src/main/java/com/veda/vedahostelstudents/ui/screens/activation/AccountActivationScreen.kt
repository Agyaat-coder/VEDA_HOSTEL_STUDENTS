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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.veda.vedahostelstudents.data.repository.ActivationResult
import com.veda.vedahostelstudents.data.repository.StudentActivationRepository
import com.veda.vedahostelstudents.ui.components.CodeInputField
import com.veda.vedahostelstudents.ui.components.VedaCard
import com.veda.vedahostelstudents.ui.components.VedaIconButton
import com.veda.vedahostelstudents.ui.components.VedaPrimaryButton
import com.veda.vedahostelstudents.ui.theme.VedaAccentSoft
import com.veda.vedahostelstudents.ui.theme.VedaBorder
import com.veda.vedahostelstudents.ui.theme.VedaCanvas
import com.veda.vedahostelstudents.ui.theme.VedaError
import com.veda.vedahostelstudents.ui.theme.VedaInk
import com.veda.vedahostelstudents.ui.theme.VedaMuted
import com.veda.vedahostelstudents.ui.theme.VedaPrimary
import com.veda.vedahostelstudents.ui.theme.VedaShapesInstance
import com.veda.vedahostelstudents.ui.theme.VedaSpacingInstance
import com.veda.vedahostelstudents.ui.theme.VedaSurface
import com.veda.vedahostelstudents.ui.theme.VedaTheme
import kotlinx.coroutines.launch

@Composable
fun AccountActivationScreen(
    onBack: () -> Unit,
    onActivateSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var codeState by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSubmitting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VedaCanvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = VedaSpacingInstance.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

            // Top Navigation Bar
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
                    enabled = !isSubmitting,
                    tint = VedaInk
                )
            }

            Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

            // VEDA HOSTEL Branding Badge (Pages 23 & 29)
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(VedaAccentSoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Key,
                    contentDescription = "Activation",
                    tint = VedaPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))

            Text(
                text = "VEDA HOSTEL",
                style = VedaTheme.typography.caption,
                color = VedaPrimary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "CAMPUS PULSE",
                style = VedaTheme.typography.caption,
                color = VedaMuted
            )

            Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

            // Activation Surface Card
            VedaCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = VedaSurface,
                borderColor = VedaBorder,
                shape = VedaShapesInstance.card,
                contentPadding = VedaSpacingInstance.cardPadding
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Activate your account",
                        style = VedaTheme.typography.screenTitle,
                        color = VedaInk
                    )

                    Spacer(modifier = Modifier.height(VedaSpacingInstance.xs))

                    Text(
                        text = "Enter the one-time activation key provided by your hostel.",
                        style = VedaTheme.typography.bodySecondary,
                        color = VedaMuted
                    )

                    Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

                    // Code Box Input
                    CodeInputField(
                        code = codeState,
                        onCodeChange = { input ->
                            if (!isSubmitting) {
                                codeState = input
                                errorMessage = null
                            }
                        }
                    )

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(VedaSpacingInstance.sm))
                        Text(
                            text = errorMessage!!,
                            style = VedaTheme.typography.caption,
                            color = VedaError
                        )
                    }

                    Spacer(modifier = Modifier.height(VedaSpacingInstance.md))

                    Text(
                        text = "e.g. VEDA-7K4P-92MX",
                        style = VedaTheme.typography.caption,
                        color = VedaMuted
                    )

                    Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

                    VedaPrimaryButton(
                        text = if (isSubmitting) "Activating..." else "Continue",
                        onClick = {
                            if (!isSubmitting) {
                                isSubmitting = true
                                errorMessage = null

                                scope.launch {
                                    val res = StudentActivationRepository.activateAccount(context, codeState)
                                    isSubmitting = false

                                    when (res) {
                                        ActivationResult.SUCCESS -> onActivateSuccess()
                                        ActivationResult.ALREADY_USED -> {
                                            errorMessage = "This activation key has already been used."
                                        }
                                        ActivationResult.ALREADY_ACTIVATED -> {
                                            errorMessage = "This student account has already been activated."
                                        }
                                        ActivationResult.INVALID_CODE -> {
                                            errorMessage = "Invalid activation key. Please check the key and try again."
                                        }
                                        ActivationResult.NETWORK_ERROR -> {
                                            errorMessage = "Network error. Please check your internet connection."
                                        }
                                        ActivationResult.PERMISSION_DENIED -> {
                                            errorMessage = "Activation service permission error. Please try again or contact the warden."
                                        }
                                        ActivationResult.SERVICE_UNAVAILABLE -> {
                                            errorMessage = "Firebase service is temporarily unavailable. Please try again."
                                        }
                                        ActivationResult.TIMEOUT -> {
                                            errorMessage = "The request timed out. Please try again."
                                        }
                                        ActivationResult.GENERIC_ERROR -> {
                                            errorMessage = "Unable to activate your account right now. Please try again."
                                        }
                                    }
                                }
                            }
                        },
                        enabled = !isSubmitting && codeState.trim().length >= 6,
                        isLoading = isSubmitting,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))

            Text(
                text = "Your key links you to your allocated hostel and room.",
                style = VedaTheme.typography.caption,
                color = VedaMuted
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Need a key?",
                    style = VedaTheme.typography.bodySecondary,
                    color = VedaMuted
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = onBack) {
                    Text(
                        text = "Ask your warden office",
                        style = VedaTheme.typography.caption,
                        color = VedaPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(VedaSpacingInstance.xl))
        }
    }
}

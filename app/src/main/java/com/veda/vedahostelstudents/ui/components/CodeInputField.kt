package com.veda.vedahostelstudents.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veda.vedahostelstudents.ui.theme.VedaBorderSubtle
import com.veda.vedahostelstudents.ui.theme.VedaBrightBlue
import com.veda.vedahostelstudents.ui.theme.VedaTextMuted
import com.veda.vedahostelstudents.ui.theme.VedaTextPrimary

@Composable
fun CodeInputField(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLength: Int = 10
) {
    BasicTextField(
        value = code,
        onValueChange = { input ->
            val uppercase = input.uppercase().take(maxLength)
            onCodeChange(uppercase)
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            keyboardType = KeyboardType.Ascii
        ),
        decorationBox = {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(maxLength) { index ->
                    val char = code.getOrNull(index)?.toString() ?: ""
                    val isFocused = index == code.length
                    val borderColor = if (isFocused || char.isNotEmpty()) VedaBrightBlue else VedaBorderSubtle

                    Box(
                        modifier = Modifier
                            .width(28.dp)
                            .height(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = if (isFocused) 2.dp else 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (char.isNotEmpty()) VedaTextPrimary else VedaTextMuted,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}

package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SelectNumberWidget(
    modifier: Modifier = Modifier,
    onNumberSelected: (Int) -> Unit,
    min: Int,
    max: Int,
    placeholder: String,
) {
    val textFieldValue = remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier,
        value = textFieldValue.value,
        onValueChange = { newValue: String ->
            textFieldValue.value = newValue
            val number = newValue.toIntOrNull()
            if (number != null && number in min..max) {
                onNumberSelected(number)
            }
        },
        placeholder = {
            Text(
                text = placeholder,
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}
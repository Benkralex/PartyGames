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
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.error_empty_input
import partygames.composeapp.generated.resources.error_valid_number
import partygames.composeapp.generated.resources.error_valid_range

@Composable
fun SelectNumberWidget(
    modifier: Modifier = Modifier,
    onNumberSelected: (Int) -> Unit,
    onInputChange: (String) -> Unit = {},
    min: Int,
    max: Int,
    label: String,
    initialValue: String = "",
) {
    val textFieldValue = remember { mutableStateOf(initialValue) }
    val validNumError: String = stringResource(Res.string.error_valid_number)
    val validRangeError: String = stringResource(Res.string.error_valid_range)
    val emptyValueError = stringResource(Res.string.error_empty_input)
    val isError = remember { mutableStateOf(initialValue.isEmpty()) }
    val errorMessage = remember { mutableStateOf(
        if (initialValue.isEmpty()) {
            emptyValueError
        } else {
            ""
        }
    ) }
    OutlinedTextField(
        modifier = modifier,
        value = textFieldValue.value,
        isError = isError.value,
        supportingText = if (isError.value) {
            {
                Text(
                    text = errorMessage.value,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        } else null,
        onValueChange = { newValue: String ->
            onInputChange(newValue)
            textFieldValue.value = newValue
            val number = newValue.toIntOrNull()
            if (number != null && number in min..max) {
                onNumberSelected(number)
                isError.value = false
            } else {
                if (number == null) {
                    if (newValue.isEmpty()) {
                        errorMessage.value = emptyValueError
                    } else {
                        errorMessage.value = validNumError
                    }
                } else {
                    errorMessage.value = validRangeError
                        .replace("%min%", min.toString())
                        .replace("%max%", max.toString())
                }
                isError.value = true
            }
        },
        label = {
            Text(
                text = label,
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        colors = OutlinedTextFieldDefaults.colors().copy(
            unfocusedLabelColor = OutlinedTextFieldDefaults.colors().unfocusedPlaceholderColor
        )
    )
}
package de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.error_empty_input
import partygames.composeapp.generated.resources.error_valid_number
import partygames.composeapp.generated.resources.error_valid_range

@Composable
fun IntegerInputWidget(
    modifier: Modifier = Modifier,
    state: IntegerInputState,
) {
    val validNumError: String = stringResource(Res.string.error_valid_number)
    val validRangeError: String = stringResource(Res.string.error_valid_range)
    val emptyValueError = stringResource(Res.string.error_empty_input)

    OutlinedTextField(
        modifier = modifier,
        value = state.realValue,
        isError = state.isError,
        supportingText = if (state.isError) {
            {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        } else null,
        onValueChange = { newValue: String ->
            state.realValue = newValue
            val number = newValue.toIntOrNull()
            if (newValue.isEmpty()) {
                state.isError = true
                state.errorMessage = emptyValueError
                return@OutlinedTextField
            }
            if (number == null) {
                state.isError = true
                state.errorMessage = validNumError
                return@OutlinedTextField
            }
            if (number !in state.min..state.max) {
                state.isError = true
                state.errorMessage = validRangeError
                    .replace("%min%", state.min.toString())
                    .replace("%max%", state.max.toString())
                return@OutlinedTextField
            }
            state.value = number
            state.isError = false
        },
        label = {
            Text(
                text = state.label,
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
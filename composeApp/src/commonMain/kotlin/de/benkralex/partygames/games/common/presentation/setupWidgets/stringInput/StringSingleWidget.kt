package de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.error_duplicate
import partygames.composeapp.generated.resources.error_empty_input

@Composable
fun StringSingleWidget(
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    state: StringSingleState,
) {
    val emptyValueError = stringResource(Res.string.error_empty_input)
    val duplicateError = stringResource(Res.string.error_duplicate)

    state.errorMessage = when {
        state.realValue.isEmpty() -> emptyValueError
        state.isDuplicate -> duplicateError
        else -> ""
    }
    state.isError = state.errorMessage.isNotEmpty()

    OutlinedTextField(
        modifier = modifier,
        isError = state.isError,
        trailingIcon = trailingIcon,
        supportingText = if (state.isError) {
            {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        } else null,
        value = state.realValue,
        onValueChange = { newValue: String ->
            state.realValue = newValue

            state.isError = newValue.isEmpty()
            if (state.isError) {
                state.errorMessage = emptyValueError
            } else {
                state.errorMessage = ""
                state.value = newValue
            }
        },
        label = {
            Text(
                text = state.label,
            )
        },
        singleLine = true,
    )
}
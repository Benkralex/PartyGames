package de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.launch
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
        state.realValue.value.text.isEmpty() -> emptyValueError
        state.isDuplicate -> duplicateError
        else -> ""
    }
    state.isError = state.errorMessage.isNotEmpty()

    val coroutineScope = rememberCoroutineScope()

    OutlinedTextField(
        modifier = modifier
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    coroutineScope.launch {
                        state.realValue.value = state.realValue.value.copy(
                            selection = TextRange(state.realValue.value.text.length, 0)
                        )
                    }
                }
            },
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
        value = state.realValue.value,
        onValueChange = { newValue: TextFieldValue ->
            state.realValue.value = newValue

            state.isError = newValue.text.isEmpty()
            if (state.isError) {
                state.errorMessage = emptyValueError
            } else {
                state.errorMessage = ""
                state.value = newValue.text
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
package de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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

    fun onValueChange(newValue: TextFieldValue) {
        state.realValue.value = newValue
        val number = newValue.text.toIntOrNull()
        if (newValue.text.isEmpty()) {
            state.isError = true
            state.errorMessage = emptyValueError
            return
        }
        if (number == null) {
            state.isError = true
            state.errorMessage = validNumError
            return
        }
        if (number !in state.min..state.max) {
            state.isError = true
            state.errorMessage = validRangeError
                .replace("%min%", state.min.toString())
                .replace("%max%", state.max.toString())
            return
        }
        state.value = number
        state.isError = false
    }

    OutlinedTextField(
        modifier = modifier,
        value = state.realValue.value,
        isError = state.isError,
        leadingIcon = {
            IconButton(
                onClick = {
                    if (state.value > state.min) {
                        onValueChange(
                            state.realValue.value.copy(
                                text = (state.value - 1).toString(),
                            )
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Remove,
                    contentDescription = "Decrease Value",
                    tint = if (state.value > state.min) {
                        LocalContentColor.current
                    } else {
                        LocalContentColor.current.copy(alpha = 0.5f)
                    },
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (state.value < state.max) {
                        onValueChange(
                            state.realValue.value.copy(
                                text = (state.value + 1).toString(),
                            )
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Increase Value",
                    tint = if (state.value < state.max) {
                        LocalContentColor.current
                    } else {
                        LocalContentColor.current.copy(alpha = 0.5f)
                    },
                )
            }
        },
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
        ),
        supportingText = if (state.isError) {
            {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        } else null,
        onValueChange = { onValueChange(it) },
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
package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.error_empty_input

@Composable
fun StringInputWidget(
    initialValue: String = "",
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val textFieldValue = remember { mutableStateOf(initialValue) }
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
        isError = isError.value,
        trailingIcon = trailingIcon,
        supportingText = if (isError.value) {
            {
                Text(
                    text = errorMessage.value,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        } else null,
        value = textFieldValue.value,
        onValueChange = { newValue: String ->
            isError.value = newValue.isEmpty()
            errorMessage.value = if (isError.value) {
                emptyValueError
            } else {
                ""
            }
            textFieldValue.value = newValue
            onValueChange(newValue)
        },
        label = {
            Text(
                text = label,
            )
        },
        singleLine = true,
    )
}
package de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.error_checkbox_too_little
import partygames.composeapp.generated.resources.error_checkbox_too_many

@Composable
fun CheckboxListWidget(
    modifier: Modifier = Modifier,
    state: CheckboxListState,
) {
    val tooManySelectedError = stringResource(Res.string.error_checkbox_too_many)
    val tooLittleSelectedError = stringResource(Res.string.error_checkbox_too_little)

    val selectedCount by remember { derivedStateOf {
        state.checkboxSingleStates.count { it.value }
    } }
    val isError by remember { derivedStateOf {
        (selectedCount < state.minCount) || (selectedCount > state.maxCount)
    } }

    state.isError = isError
    state.errorMessage = when {
        selectedCount < state.minCount -> tooLittleSelectedError.replace("%min%", state.minCount.toString())
        selectedCount > state.maxCount -> tooManySelectedError.replace("%max%", state.maxCount.toString())
        state.isError -> state.errorMessage // Preserve existing error message if set
        else -> ""
    }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        Text(
            text = state.label,
            style = MaterialTheme.typography.titleLarge,
        )
        if (state.isError) {
            Text(
                text = state.errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
            )
        }
        state.checkboxSingleStates.forEach { checkboxState ->
            key (checkboxState.label, checkboxState.value) {
                CheckboxSingleWidget(
                    modifier = Modifier.fillMaxWidth(),
                    state = checkboxState,
                )
            }
        }
    }
}
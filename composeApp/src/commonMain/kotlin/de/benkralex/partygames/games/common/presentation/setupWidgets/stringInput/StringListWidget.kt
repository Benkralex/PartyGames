package de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.content_description_add_string_button
import partygames.composeapp.generated.resources.content_description_remove_string_button

@Composable
fun StringListWidget(
    modifier: Modifier = Modifier,
    state: StringListState,
) {
    if (state.noDuplicates) {
        val allValues by remember (state.stringSingleStates.values) { derivedStateOf {
            state.stringSingleStates.values.map { it.value.trim() }
        } }
        val duplicates by remember (allValues) { derivedStateOf {
            allValues.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
        } }

        state.stringSingleStates.values.forEach { state ->
            val currentValue = state.value.trim()
            state.isDuplicate = currentValue.isNotEmpty() && duplicates.contains(currentValue)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = state.label,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { state.addStringState() },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(Res.string.content_description_add_string_button),
                )
            }
        }
        state.stringSingleStates.forEach { entry ->
            val index = entry.key
            val stringSingleState = entry.value
            key(index) {
                StringSingleWidget(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = stringSingleState,
                    trailingIcon = {
                        IconButton(
                            onClick = { state.removeStringState(index) },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(Res.string.content_description_remove_string_button),
                                tint = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                )
            }
        }
    }
}
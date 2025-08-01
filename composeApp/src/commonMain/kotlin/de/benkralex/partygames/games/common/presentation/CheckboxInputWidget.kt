package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxInputWidget(
    modifier: Modifier = Modifier,
    label: String,
    options: Map<String, Boolean>,
    onValueChange: (Map<String, Boolean>) -> Unit,
) {
    val options = options.toMutableMap()
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
        )
        options.forEach { (option, isChecked) ->
            key (option, isChecked) {
                CheckboxSingleWidget(
                    modifier = Modifier.fillMaxWidth(),
                    label = option,
                    initialValue = isChecked,
                    onToggled = { newValue ->
                        options[option] = newValue
                        onValueChange(options)
                    },
                )
            }
        }
    }
}
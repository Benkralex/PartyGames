package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxSingleWidget(
    modifier: Modifier = Modifier,
    onToggled: (Boolean) -> Unit,
    label: String,
    initialValue: Boolean = false,
) {
    var isChecked by remember { mutableStateOf(initialValue) }
    Row(
        modifier = modifier
            .clickable(
                onClick = {
                    isChecked = !isChecked
                    onToggled(isChecked)
                },
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { newValue ->
                isChecked = newValue
                onToggled(newValue)
            },
        )
        Text(
            text = label,
        )
    }
}
package de.benkralex.partygames.games.common.presentation.setupWidgets.switchInput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
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
fun SelectBooleanWidget(
    modifier: Modifier = Modifier,
    onBooleanSelected: (Boolean) -> Unit,
    label: String,
    initialValue: Boolean = false,
) {
    var isChecked by remember { mutableStateOf(initialValue) }
        Row(
            modifier = modifier
                .clickable(
                    onClick = {
                        isChecked = !isChecked
                        onBooleanSelected(isChecked)
                    },
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
            )
            Box(modifier = Modifier.weight(1f))
            Switch(
                checked = isChecked,
                onCheckedChange = { newValue ->
                    isChecked = newValue
                    onBooleanSelected(newValue)
                },
            )
        }
}
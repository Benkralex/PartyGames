package de.benkralex.partygames.games.common.presentation.setupWidgets.switchInput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SwitchSingleWidget(
    modifier: Modifier = Modifier,
    state: SwitchSingleState,
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = {
                    state.value = !state.value
                },
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = state.label,
        )
        Box(modifier = Modifier.weight(1f))
        Switch(
            checked = state.value,
            onCheckedChange = { newValue ->
                state.value = newValue
            },
        )
    }
}
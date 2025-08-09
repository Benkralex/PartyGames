package de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxSingleWidget(
    modifier: Modifier = Modifier,
    state: CheckboxSingleState,
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
        Checkbox(
            checked = state.value,
            onCheckedChange = { newValue ->
                state.value = newValue
            },
        )
        Text(
            text = state.label[Locale.current.language + "_" + Locale.current.region],
        )
    }
}
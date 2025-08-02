package de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class CheckboxSingleState(
    val label: String,
    defaultValue: Boolean,
) {
    var value by mutableStateOf(defaultValue)
}
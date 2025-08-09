package de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.benkralex.partygames.games.common.domain.TranslatableString
import org.jetbrains.compose.resources.StringResource

class CheckboxSingleState(
    val label: TranslatableString,
    defaultValue: Boolean,
) {
    var value by mutableStateOf(defaultValue)
}
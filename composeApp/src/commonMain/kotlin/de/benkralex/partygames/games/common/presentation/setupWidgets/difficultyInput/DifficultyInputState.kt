package de.benkralex.partygames.games.common.presentation.setupWidgets.difficultyInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import de.benkralex.partygames.games.common.domain.Difficulty

class DifficultyInputState(
    var label: String,
    defaultValue: Difficulty,
) {
    var value by mutableStateOf(defaultValue)
    val realValue = mutableStateOf(TextFieldValue(defaultValue.toString()))
    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
}
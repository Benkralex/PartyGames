package de.benkralex.partygames.games.common.presentation.setupWidgets.difficultyInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.benkralex.partygames.games.common.domain.Difficulty

class DifficultyInputState(
    val label: String,
    defaultValue: Difficulty,
) {
    var value by mutableStateOf(defaultValue)
    var realValue by mutableStateOf(defaultValue.toString())
    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
}
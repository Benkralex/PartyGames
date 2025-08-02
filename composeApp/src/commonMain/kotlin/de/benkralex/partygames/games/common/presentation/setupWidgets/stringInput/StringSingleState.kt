package de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class StringSingleState(
    val label: String,
    defaultValue: String,
) {
    var value by mutableStateOf(defaultValue)
    var realValue by mutableStateOf(defaultValue)
    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var isDuplicate by mutableStateOf(false)
}
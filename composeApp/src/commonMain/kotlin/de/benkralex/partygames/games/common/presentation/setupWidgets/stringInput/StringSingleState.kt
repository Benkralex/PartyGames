package de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

class StringSingleState(
    var label: String,
    defaultValue: String,
) {
    var value by mutableStateOf(defaultValue)
    val realValue = mutableStateOf(TextFieldValue(defaultValue))
    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var isDuplicate by mutableStateOf(false)
}
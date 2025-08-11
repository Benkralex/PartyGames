package de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

class IntegerInputState(
    var label: String,
    defaultValue: Int,
    min: Int = Int.MIN_VALUE,
    max: Int = Int.MAX_VALUE,
) {
    var value by mutableStateOf(defaultValue)
    val realValue = mutableStateOf(TextFieldValue(defaultValue.toString()))
    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var min by mutableStateOf(min)
    var max by mutableStateOf(max)
}
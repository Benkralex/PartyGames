package de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class IntegerInputState(
    val label: String,
    defaultValue: Int,
    min: Int = Int.MIN_VALUE,
    max: Int = Int.MAX_VALUE,
) {
    var value by mutableStateOf(defaultValue)
    var realValue by mutableStateOf(defaultValue.toString())
    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var min by mutableStateOf(min)
    var max by mutableStateOf(max)
}
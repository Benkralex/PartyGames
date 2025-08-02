package de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.collections.plus

class StringListState(
    val label: String,
    stringSingleStates: Map<Int, StringSingleState>,
    val minCount: Int = 0,
    val maxCount: Int = Int.MAX_VALUE,
    val textFieldLabel: String = "",
    val defaultValue: String = "",
    val noDuplicates: Boolean = false,
) {
    var stringSingleStates by mutableStateOf(stringSingleStates)
        private set

    fun addStringState() {
        if (maxCount != -1 && stringSingleStates.size >= maxCount) {
            return
        }
        val nextKey = (stringSingleStates.keys.maxOrNull() ?: 0) + 1
        stringSingleStates = stringSingleStates.plus(
            nextKey to StringSingleState(
                label = textFieldLabel,
                defaultValue = defaultValue,
            )
        )
    }

    fun removeStringState(index: Int) {
        if (stringSingleStates.size <= minCount) {
            return
        }
        stringSingleStates = stringSingleStates.minus(index)
    }
}
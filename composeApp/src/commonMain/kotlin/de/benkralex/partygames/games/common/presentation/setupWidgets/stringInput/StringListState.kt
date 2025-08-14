package de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.collections.plus

class StringListState(
    var label: String,
    stringSingleStates: Map<Int, StringSingleState>,
    val minCount: Int = 0,
    val maxCount: Int = Int.MAX_VALUE,
    var textFieldLabel: String = "",
    var defaultValue: (Int) -> String,
    val noDuplicates: Boolean = false,
) {
    var stringSingleStates by mutableStateOf(stringSingleStates)
        private set

    fun addStringState() {
        if (maxCount != -1 && stringSingleStates.size >= maxCount) {
            return
        }
        val nextKey = (stringSingleStates.keys.maxOrNull() ?: 0) + 1
        var nextValue = ""
        var i = 1
        do {
            nextValue = defaultValue(i)
            i++
        } while (nextValue in stringSingleStates.map { it.value.value })
        stringSingleStates = stringSingleStates.plus(
            nextKey to StringSingleState(
                label = textFieldLabel,
                defaultValue = nextValue,
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
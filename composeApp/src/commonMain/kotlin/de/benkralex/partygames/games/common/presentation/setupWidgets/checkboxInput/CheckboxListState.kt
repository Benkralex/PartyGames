package de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput

class CheckboxListState(
    var label: String,
    var checkboxSingleStates: List<CheckboxSingleState>,
    val minCount: Int = 0,
    val maxCount: Int = Int.MAX_VALUE,
    var isError: Boolean = false,
    var errorMessage: String = "",
)
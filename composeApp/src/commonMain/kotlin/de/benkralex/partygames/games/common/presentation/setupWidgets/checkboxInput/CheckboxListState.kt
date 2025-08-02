package de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput

class CheckboxListState(
    val label: String,
    val checkboxSingleStates: List<CheckboxSingleState>,
    val minCount: Int = 0,
    val maxCount: Int = checkboxSingleStates.size,
    var isError: Boolean = false,
    var errorMessage: String = "",
)
package de.benkralex.partygames.games.findLiar.presentation

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput.CheckboxListState
import de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput.CheckboxSingleState
import de.benkralex.partygames.games.common.presentation.setupWidgets.difficultyInput.DifficultyInputState
import de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput.IntegerInputState
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringListState
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringSingleState
import io.github.aakira.napier.Napier
import kotlin.text.compareTo
import kotlin.toString

class FindLiarSetupViewModel: ViewModel() {

    private val _playersState = mutableStateOf<StringListState?>(null)
    val playersState: StringListState get() = _playersState.value!!

    val players by derivedStateOf {
        _playersState.value?.stringSingleStates
            ?.map { it.value.value }
            ?.filter { it.isNotEmpty() }
            ?: emptyList()
    }

    val playerCount by derivedStateOf {
        players.size
    }

    val liarCountState = IntegerInputState(
        label = "",
        min = 1,
        max = 2,
        defaultValue = 1,
    )

    val liarCount by derivedStateOf {
        liarCountState.value
    }

    val difficultyState = DifficultyInputState(
        label = "",
        defaultValue = Difficulty.MEDIUM,
    )

    val difficulty by derivedStateOf {
        difficultyState.value
    }

    val topicsState = CheckboxListState(
        label = "",
        checkboxSingleStates = listOf(
            CheckboxSingleState("Lorem ipsum1", true),
            CheckboxSingleState("Lorem ipsum2", false),
            CheckboxSingleState("Lorem ipsum3", false),
            CheckboxSingleState("Lorem ipsum4", true),
            CheckboxSingleState("Lorem ipsum5", true),
        ),
        minCount = 1,
    )

    val topics by derivedStateOf {
        topicsState.checkboxSingleStates
            .filter { it.value }
            .map { it.label }
    }

    fun initializeLabels(
        playerListLabel: String,
        playerSingleLabel: String,
        playerNameStart: String,
        liarCountLabel: String,
        difficultyLabel: String,
        topicsLabel: String
    ) {
        if (_playersState.value == null) {
            _playersState.value = StringListState(
                label = playerListLabel,
                stringSingleStates = mapOf(
                    0 to StringSingleState(label = playerSingleLabel, defaultValue = "$playerNameStart 1"),
                    1 to StringSingleState(label = playerSingleLabel, defaultValue = "$playerNameStart 2"),
                    2 to StringSingleState(label = playerSingleLabel, defaultValue = "$playerNameStart 3"),
                ),
                minCount = 3,
                textFieldLabel = playerSingleLabel,
                defaultValue = playerNameStart,
                noDuplicates = true,
            )
        }

        liarCountState.label = liarCountLabel
        difficultyState.label = difficultyLabel
        topicsState.label = topicsLabel
    }

    fun updateLiarCountConstraints() {
        liarCountState.max = maxOf(1, playerCount - 1)
        if (liarCountState.value > liarCountState.max) {
            liarCountState.value = liarCountState.max
            liarCountState.realValue = liarCountState.max.toString()
        }
    }

    // Rest der Methoden bleibt gleich
    fun setupGame(setupGameCallback: (List<String>, Int, List<String>, Difficulty) -> Unit) {
        Napier.i(
            "players=$players," +
                    " liarCount=${liarCount}," +
                    " topics=$topics," +
                    " difficulty=${difficulty}"
        )

        if (isSetupInvalid()) {
            Napier.e("Setup is invalid, cannot start game")
            return
        }

        setupGameCallback(players, liarCount, topics, difficulty)
    }

    private fun isSetupInvalid(): Boolean {
        return _playersState.value?.stringSingleStates?.values?.map { it.isError }?.any { it } == true ||
                liarCountState.isError ||
                difficultyState.isError ||
                topicsState.isError
    }
}
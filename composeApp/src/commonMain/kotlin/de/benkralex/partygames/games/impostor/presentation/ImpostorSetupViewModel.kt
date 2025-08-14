package de.benkralex.partygames.games.impostor.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.lifecycle.ViewModel
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput.CheckboxListState
import de.benkralex.partygames.games.common.presentation.setupWidgets.difficultyInput.DifficultyInputState
import de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput.IntegerInputState
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringListState
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringSingleState
import de.benkralex.partygames.settingsPage.data.Settings
import de.benkralex.partygames.settingsPage.data.settings
import io.github.aakira.napier.Napier

class ImpostorSetupViewModel: ViewModel() {

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

    val impostorCountState = IntegerInputState(
        label = "",
        min = 1,
        max = 2,
        defaultValue = 1,
    )

    val impostorCount by derivedStateOf {
        impostorCountState.value
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
        checkboxSingleStates = emptyList(),
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
        impostorCountLabel: String,
        difficultyLabel: String,
        topicsLabel: String
    ) {
        if (_playersState.value == null) {
            _playersState.value = StringListState(
                label = playerListLabel,
                stringSingleStates = settings.value.lastPlayers.filter { it.trim().isNotBlank() }.mapIndexed { index, playerName ->
                    index to StringSingleState(
                        label = playerSingleLabel,
                        defaultValue = playerName
                    )
                }.toMap().plus(
                    map = (settings.value.lastPlayers.filter { it.trim().isNotBlank() }.size until 3).associateWith { i ->
                        StringSingleState(
                            label = playerSingleLabel,
                            defaultValue = "$playerNameStart ${i + 1}"
                        )
                    }
                ),
                minCount = 3,
                textFieldLabel = playerSingleLabel,
                defaultValue = { "$playerNameStart $it" },
                noDuplicates = true,
            )
        }

        impostorCountState.label = impostorCountLabel
        difficultyState.label = difficultyLabel
        topicsState.label = topicsLabel
    }

    fun updateImpostorCountConstraints() {
        impostorCountState.max = maxOf(1, playerCount - 1)
        if (impostorCountState.value > impostorCountState.max) {
            impostorCountState.value = impostorCountState.max
            impostorCountState.realValue.value = impostorCountState.realValue.value.copy(
                text = impostorCountState.max.toString(),
                selection = TextRange(0),
            )
        }
    }

    fun setupGame(setupGameCallback: (List<String>, Int, List<TranslatableString>, Difficulty) -> Unit) {
        Napier.i(
            "players=$players," +
                    " impostorCount=${impostorCount}," +
                    " topics=$topics," +
                    " difficulty=${difficulty}"
        )

        if (isSetupInvalid()) {
            Napier.e("Setup is invalid, cannot start game")
            return
        }
        val newSettings = Settings(
            languages = settings.value.languages,
            lastPlayers = players
        )
        settings.value = newSettings
        setupGameCallback(players, impostorCount, topics, difficulty)
    }

    private fun isSetupInvalid(): Boolean {
        return _playersState.value?.stringSingleStates?.values?.map { it.isError }?.any { it } == true ||
                impostorCountState.isError ||
                difficultyState.isError ||
                topicsState.isError
    }
}
package de.benkralex.partygames.games.justOne.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput.CheckboxListState
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringListState
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringSingleState
import de.benkralex.partygames.settingsPage.data.settings
import io.github.aakira.napier.Napier

class JustOneSetupViewModel: ViewModel() {

    private val _playersState = mutableStateOf<StringListState?>(null)
    val playersState: StringListState get() = _playersState.value!!

    val players by derivedStateOf {
        _playersState.value?.stringSingleStates
            ?.map { it.value.value }
            ?.filter { it.isNotEmpty() }
            ?: emptyList()
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
        topicsLabel: String
    ) {
        if (_playersState.value == null) {
            val lastPlayers = settings.value.lastPlayers.filter { it.trim().isNotBlank() }
            _playersState.value = StringListState(
                label = playerListLabel,
                stringSingleStates = lastPlayers.mapIndexed { index, playerName ->
                    index to StringSingleState(
                        label = playerSingleLabel,
                        defaultValue = playerName
                    )
                }.toMap().plus(
                    map = (lastPlayers.size until 3).associateWith { i ->
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

        topicsState.label = topicsLabel
    }

    fun setupGame(setupGameCallback: (List<String>, List<TranslatableString>) -> Unit) {
        Napier.i(
            "players=$players," +
                    " topics=$topics"
        )

        if (isSetupInvalid()) {
            Napier.e("Setup is invalid, cannot start game")
            return
        }
        settings.value = settings.value.copy(
            lastPlayers = players
        )
        setupGameCallback(players, topics)
    }

    private fun isSetupInvalid(): Boolean {
        return _playersState.value?.stringSingleStates?.values?.map { it.isError }?.any { it } == true ||
                topicsState.isError
    }
}
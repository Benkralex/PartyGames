package de.benkralex.partygames.games.justOne.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.justOne.domain.JustOne
import de.benkralex.partygames.games.justOne.domain.JustOneDataset
import io.github.aakira.napier.Napier

class JustOnePlayViewModel : ViewModel() {
    var datasets: List<JustOneDataset> = emptyList()
    var game: JustOne? = null
    val players: List<String> by derivedStateOf {
        (game?.settings?.get("players") as? List<*>)?.map {
            it as? String
        }?.filter { it != null }?.map { it!! } ?: emptyList()
    }
    val topics: List<Pair<String, TranslatableString>> by derivedStateOf {
        (game?.settings?.get("topics") as? List<Pair<String, TranslatableString>>) ?: emptyList()
    }
    val words by derivedStateOf {
        datasets.flatMap { it.words }
            .filter { topics.map { it.first }.contains(it.topic_key) }
            .map { it.word }
    }
    var currentWord: TranslatableString? = null
    var playedWords: MutableList<TranslatableString> = mutableListOf()
    lateinit var detective: String
    val previousDetectives = mutableListOf<String>()
    var activePlayer: String? by mutableStateOf(null)
    var finishedPlayers: MutableList<String> = mutableListOf()

    var hints: MutableMap<String,String> = mutableMapOf()

    var phase: Phase by mutableStateOf(Phase.INITIALIZE)

    fun initNewRound() {
        if (game == null) {
            Napier.e("Game is not initialized yet")
            return
        }
        if (words.none { !playedWords.contains(it) }) {
            Napier.e("No question pairs available for the selected topics")
            return
        }

        currentWord = words.filter { !playedWords.contains(it) }.random()
        playedWords.add(currentWord!!)
        finishedPlayers.clear()
        hints.clear()

        if(previousDetectives.containsAll(players)) {
            previousDetectives.clear()
        }
        detective = players.filter { !previousDetectives.contains(it) }.random()
        previousDetectives.add(detective)

        phase = Phase.SHOW_DETECTIVE
    }

    fun onProvideHint(hint: String) {
        hints[activePlayer!!] = hint
        updateActivePlayer()
    }

    fun updateActivePlayer() {
        if (activePlayer != null) {
            finishedPlayers.add(activePlayer!!)
        }
        activePlayer = players.firstOrNull { !finishedPlayers.contains(it) && it != detective }
        if(activePlayer == null) {
            showHints()
        }
    }

    fun showHints() {
        val duplicates = mutableSetOf<String>()

        val values = hints.values.toList()
        values.forEachIndexed { i, value ->
            val firstIndex = values.indexOfFirst { other -> other.trim().equals(value.trim(), ignoreCase = true) }
            if(firstIndex != i) {
                duplicates += value
                duplicates += values[firstIndex]
            }
        }

        hints = hints.filter { !duplicates.contains(it.value) }.toMutableMap()

        phase = Phase.SHOW_HINTS
    }
}

enum class Phase {
    INITIALIZE,
    SHOW_DETECTIVE,
    PROVIDE_HINT,
    SHOW_HINTS,
    SHOW_WORD
}
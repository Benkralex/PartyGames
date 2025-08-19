package de.benkralex.partygames.games.impostor.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.impostor.data.getImposterWordPairs
import de.benkralex.partygames.games.impostor.domain.Impostor
import de.benkralex.partygames.games.impostor.domain.ImpostorWordPair
import io.github.aakira.napier.Napier

class ImpostorPlayViewModel : ViewModel() {
    var game: Impostor? = null
    val players: List<String> by derivedStateOf {
        game?.settings?.get("players") as? List<String> ?: emptyList()
    }
    val topics: List<TranslatableString> by derivedStateOf {
        game?.settings?.get("topics") as? List<TranslatableString> ?: emptyList()
    }
    val impostorCount: Int by derivedStateOf {
        game?.settings?.get("impostorCount") as? Int ?: 1
    }
    val playerCount: Int by derivedStateOf {
        players.size
    }
    val wordPairs by derivedStateOf {
        getImposterWordPairs(listOf(Locale.current.language)).filter { topics.contains(it.topic) }
    }


    var impostors: List<String> = emptyList()
    var currentWordPair: ImpostorWordPair? = null
    var playedWordPairs: MutableList<ImpostorWordPair> = mutableListOf()
    var activePlayer: String? by mutableStateOf(null)
    var word: TranslatableString? by mutableStateOf(null)
    var finishedPlayers: MutableList<String> = mutableListOf()

    fun initNewRound() {
        if (game == null) {
            Napier.e("Game is not initialized yet")
            return
        }
        if (wordPairs.none { !playedWordPairs.contains(it) }) {
            Napier.e("No question pairs available for the selected topics")
            return
        }

        impostors = emptyList()
        while (impostors.size != impostorCount) {
            val p: String = players.random()
            if (p in impostors) continue
            impostors += p
        }

        currentWordPair = wordPairs.filter { !playedWordPairs.contains(it) }.random()
        playedWordPairs.add(currentWordPair!!)
        finishedPlayers.clear()
        updateActivePlayer()
    }

    fun updateActivePlayer() {
        if (activePlayer != null) {
            finishedPlayers.add(activePlayer!!)
        }
        activePlayer = players.firstOrNull { !finishedPlayers.contains(it) }
        word = when (activePlayer) {
            null -> {
                null
            }
            in impostors -> {
                currentWordPair?.impostorHintWord
            }
            else -> {
                currentWordPair?.mainWord
            }
        }
    }
}
package de.benkralex.partygames.games.impostor.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.impostor.data.ImpostorWordPair
import de.benkralex.partygames.games.impostor.data.getImposterWordSets
import de.benkralex.partygames.games.impostor.domain.Impostor
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
    val difficulty: Difficulty by derivedStateOf {
        game?.settings?.get("difficulty") as? Difficulty ?: Difficulty.MEDIUM
    }
    val wordPairs by derivedStateOf {
        getImposterWordSets(listOf(Locale.current.language)).filter { it.difficulty == difficulty }.filter { topics.contains(it.topic) }
    }


    var impostors: List<String> = emptyList()
    var currentWordPair: ImpostorWordPair? = null
    var playedWordPairs: MutableList<ImpostorWordPair> = mutableListOf()
    var activePlayer: String? by mutableStateOf(null)
    var word: TranslatableString? by mutableStateOf(null)
    var finishedPlayers: MutableList<String> = mutableListOf()

    fun initNewRound() {
        if (game == null) {
            Napier.e("Game is not initialized")
            return
        }
        if (wordPairs.none { !playedWordPairs.contains(it) }) {
            Napier.e("No question pairs available for the selected topics and difficulty")
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
        Napier.d("New round initialized with question: ${currentWordPair?.mainWord} and impostors: $impostors")
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
                Napier.d("All players have answered the question")
                null
            }
            in impostors -> {
                Napier.d("Current answering player is a liar: $activePlayer")
                currentWordPair?.impostorHintWord
            }
            else -> {
                Napier.d("Current answering player is not a liar: $activePlayer")
                currentWordPair?.mainWord
            }
        }
    }
}
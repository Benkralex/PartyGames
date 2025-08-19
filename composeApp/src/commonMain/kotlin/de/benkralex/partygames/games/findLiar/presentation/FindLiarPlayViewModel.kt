package de.benkralex.partygames.games.findLiar.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.findLiar.data.FindLiarQuestionPair
import de.benkralex.partygames.games.findLiar.data.getFindLiarQuestionPairs
import de.benkralex.partygames.games.findLiar.domain.FindLiar
import io.github.aakira.napier.Napier

class FindLiarPlayViewModel : ViewModel() {
    var game: FindLiar? = null
    val players: List<String> by derivedStateOf {
        game?.settings?.get("players") as? List<String> ?: emptyList()
    }
    val topics: List<TranslatableString> by derivedStateOf {
        game?.settings?.get("topics") as? List<TranslatableString> ?: emptyList()
    }
    val liarCount: Int by derivedStateOf {
        game?.settings?.get("liarCount") as? Int ?: 1
    }
    val playerCount: Int by derivedStateOf {
        players.size
    }
    val questionPairs by derivedStateOf {
        getFindLiarQuestionPairs(listOf(Locale.current.language)).filter { topics.contains(it.topic) }
    }


    var liars: List<String> = emptyList()
    var currentQuestionPair: FindLiarQuestionPair? = null
    var playedQuestions: MutableList<FindLiarQuestionPair> = mutableListOf()
    var answeringPlayer: String? by mutableStateOf(null)
    var question: TranslatableString? by mutableStateOf(null)
    var answers: MutableMap<String, String> = mutableMapOf()

    fun initNewRound() {
        if (game == null) {
            Napier.e("Game is not initialized yet")
            return
        }
        if (questionPairs.none { !playedQuestions.contains(it) }) {
            Napier.e("No question pairs available for the selected topics and difficulty")
            return
        }

        liars = emptyList()
        while (liars.size != liarCount) {
            val p: String = players.random()
            if (p in liars) continue
            liars += p
        }

        currentQuestionPair = questionPairs.filter { !playedQuestions.contains(it) }.random()
        playedQuestions.add(currentQuestionPair!!)
        if (currentQuestionPair!!.switchable && (0..1).random() == 0) {
            currentQuestionPair = FindLiarQuestionPair(
                switchable = true,
                mainQuestion = currentQuestionPair!!.liarQuestion,
                liarQuestion = currentQuestionPair!!.mainQuestion,
                topic = currentQuestionPair!!.topic
            )
        }
        answers.clear()
        updateAnsweringPlayer()
    }

    fun answerQuestion(player: String, answer: String) {
        answers[player] = answer
        updateAnsweringPlayer()
    }

    fun updateAnsweringPlayer() {
        answeringPlayer = players.firstOrNull { !answers.containsKey(it) }
        question = when (answeringPlayer) {
            null -> {
                null
            }
            in liars -> {
                currentQuestionPair?.liarQuestion
            }
            else -> {
                currentQuestionPair?.mainQuestion
            }
        }
    }
}
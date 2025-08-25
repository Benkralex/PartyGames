package de.benkralex.partygames.games.findLiar.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.findLiar.domain.FindLiar
import de.benkralex.partygames.games.findLiar.domain.FindLiarDataset
import de.benkralex.partygames.games.findLiar.domain.FindLiarQuestionPair
import de.benkralex.partygames.settingsPage.data.settings
import io.github.aakira.napier.Napier

class FindLiarPlayViewModel : ViewModel() {
    var datasets: List<FindLiarDataset> = emptyList()
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
        val languages = settings.value.languages
        datasets.flatMap { it.questionPairs }.filter { q ->
            q.liarQuestion.translations.keys.any { lang ->
                lang in languages
                        || lang.split("_")[0] in languages
                        || lang in languages.map { it.split("_")[0] }
            } && q.mainQuestion.translations.keys.any { lang ->
                lang in languages
                        || lang.split("_")[0] in languages
                        || lang in languages.map { it.split("_")[0] }
            }
        }.filter { topics.contains(it.topic) }
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
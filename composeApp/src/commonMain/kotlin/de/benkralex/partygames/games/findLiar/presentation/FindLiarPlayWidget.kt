package de.benkralex.partygames.games.findLiar.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import de.benkralex.partygames.app.activeGame
import de.benkralex.partygames.app.getGameByKey
import de.benkralex.partygames.games.findLiar.data.FindLiar
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource

@Composable
fun FindLiarPlayWidget(
    modifier: Modifier = Modifier,
    viewModel: FindLiarPlayViewModel = viewModel<FindLiarPlayViewModel>(key = activeGame),
) {
    if (activeGame == null) {
        Napier.e("No active game found")
        Text("No active game found")
        return
    }
    val game = getGameByKey(activeGame!!)
    if (game == null) {
        Napier.e("Game with key $activeGame not found")
        Text("Game with key $activeGame not found")
        return
    }
    if (game !is FindLiar) {
        Napier.e("Game with key $activeGame is not a FindLiar game")
        Text("Game with key $activeGame is not a FindLiar game")
        return
    }

    LaunchedEffect(game) {
        viewModel.game = game
        viewModel.initNewRound()
    }

    Column (
        modifier = modifier,
    ) {
        key(viewModel.game, viewModel.answeringPlayer, viewModel.question) {
            if (viewModel.game == null) {
                Napier.i("Game ist null, zeige Initialisierungsnachricht")
                Text("Spiel wird initialisiert...")
            } else if (viewModel.question != null && viewModel.answeringPlayer != null) {
                Napier.i("Zeige Frage und antwortenden Spieler: ${viewModel.answeringPlayer}")
                Text(
                    text = stringResource(viewModel.question!!)
                )
                Text(
                    text = viewModel.answeringPlayer!!
                )
                var answer by remember {mutableStateOf("")}
                OutlinedTextField(
                    value = answer,
                    onValueChange = { newAnswer: String ->
                        Napier.i("Antwort geändert: $newAnswer")
                        answer = newAnswer
                    },
                )
                Button(
                    onClick = {
                        viewModel.answerQuestion(viewModel.answeringPlayer!!, answer)
                        Napier.i("Antwort von ${viewModel.answeringPlayer} eingereicht")
                    }
                ) {
                    Text("Antwort einreichen")
                }
            } else if (viewModel.answeringPlayer == null && viewModel.answers.size == viewModel.playerCount) {
                Napier.i("Alle Spieler haben geantwortet: ${viewModel.answers.size}/${viewModel.playerCount}")
                Text(
                    text = "Alle Spieler haben geantwortet: ${viewModel.answers}"
                )
            } else {
                Napier.i("Ladestatus - answeringPlayer: ${viewModel.answeringPlayer}, answers: ${viewModel.answers.size}/${viewModel.playerCount}")
                Text(
                    text = "Laden..."
                )
            }
        }
    }
}
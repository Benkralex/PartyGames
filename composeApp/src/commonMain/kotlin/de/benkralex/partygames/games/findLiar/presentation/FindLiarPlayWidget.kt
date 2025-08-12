package de.benkralex.partygames.games.findLiar.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.benkralex.partygames.app.activeGame
import de.benkralex.partygames.app.getGameByKey
import de.benkralex.partygames.games.findLiar.domain.FindLiar
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.find_liar_res_show_liars
import partygames.composeapp.generated.resources.new_round

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
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        key(viewModel.game, viewModel.answeringPlayer, viewModel.question) {
            if (viewModel.game != null && viewModel.question != null && viewModel.answeringPlayer != null) {
                AskQuestionCard(
                    question = viewModel.question!![Locale.current.language + "_" + Locale.current.region],
                    player = viewModel.answeringPlayer!!,
                    onAnswer = { answer: String ->
                        Napier.i("Antwort von ${viewModel.answeringPlayer} eingereicht: $answer")
                        viewModel.answerQuestion(viewModel.answeringPlayer!!, answer)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp,
                        ),
                )
            } else if (viewModel.game != null && viewModel.answeringPlayer == null && viewModel.answers.size == viewModel.playerCount) {
                ShowAnswers(
                    answers = viewModel.answers,
                    question = viewModel.currentQuestionPair!!.mainQuestion[Locale.current.language + "_" + Locale.current.region],
                    liars = viewModel.liars,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    newRoundCallback = {
                        viewModel.initNewRound()
                    },
                )
            }
        }
    }
}

@Composable
fun AskQuestionCard(
    modifier: Modifier = Modifier,
    question: String,
    player: String,
    onAnswer: (String) -> Unit,
) {
    var answer by remember { mutableStateOf("") }
    var opened by remember { mutableStateOf(false) }
    val focusRequester = remember { androidx.compose.ui.focus.FocusRequester() }
    LaunchedEffect(opened) {
        if (opened) {
            focusRequester.requestFocus()
        }
    }
    Card (
        modifier = modifier
            .padding(8.dp)
            .clickable { opened = true },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (!opened) {
                Text(
                    text = player,
                    style = MaterialTheme.typography.displayLarge
                )
            } else {
                Text(
                    text = question,
                    style = MaterialTheme.typography.headlineSmall,
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .focusRequester(focusRequester),
                    value = answer,
                    onValueChange = { newAnswer: String ->
                        answer = newAnswer
                    },
                    singleLine = true,
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    onClick = {
                        onAnswer(answer)
                    }
                ) {
                    Text("Fertig")
                }
            }
        }
    }
}

@Composable
fun ShowAnswers(
    modifier: Modifier = Modifier,
    answers: Map<String, String>,
    question: String,
    liars: List<String>,
    newRoundCallback: () -> Unit = {}
) {
    var showLiars by remember { mutableStateOf(false) }
    Column {
        Text(
            modifier = Modifier
                .padding(12.dp),
            text = question,
            style = MaterialTheme.typography.headlineSmall,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .weight(1f)
        ) {
            items(answers.entries.toList()) { (player, answer) ->
                val liarColor = MaterialTheme.colorScheme.error
                val defaultColor = MaterialTheme.colorScheme.outline
                val boarder by derivedStateOf {
                    if (showLiars && player in liars) {
                        BorderStroke(2.dp, liarColor)
                    } else {
                        BorderStroke(1.dp, defaultColor)
                    }
                }
                val defaultColors = CardDefaults.cardColors()
                val liarColors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.3f),
                )
                val colors by derivedStateOf {
                    if (showLiars && player in liars) {
                        liarColors
                    } else {
                        defaultColors
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(125.dp),
                    border = boarder,
                    colors = colors,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp),
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = player,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = answer,
                            style = MaterialTheme.typography.bodyLarge,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                if (!showLiars) {
                    showLiars = true
                } else {
                    newRoundCallback()
                    showLiars = false
                }
            }
        ) {
            val buttonText = if (!showLiars) {
                stringResource(Res.string.find_liar_res_show_liars)
            } else {
                stringResource(Res.string.new_round)
            }
            Text(
                text = buttonText,
            )
        }
    }
}
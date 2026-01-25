package de.benkralex.partygames.games.justOne.presentation

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import de.benkralex.partygames.app.local
import de.benkralex.partygames.games.justOne.domain.JustOne
import de.benkralex.partygames.games.justOne.domain.JustOneDataset
import de.benkralex.partygames.games.justOne.domain.JustOnePhase
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.accept_dialog
import partygames.composeapp.generated.resources.just_one_res_guess
import partygames.composeapp.generated.resources.just_one_res_hint
import partygames.composeapp.generated.resources.just_one_res_is_detective
import partygames.composeapp.generated.resources.just_one_res_show_word
import partygames.composeapp.generated.resources.just_one_res_submit
import partygames.composeapp.generated.resources.new_round
import kotlin.reflect.KClass
import kotlin.reflect.cast

@Composable
fun JustOnePlayWidget(
    modifier: Modifier = Modifier,
    game: JustOne,
    vm: JustOnePlayViewModel = viewModel<JustOnePlayViewModel>(
        factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return modelClass.cast(JustOnePlayViewModel())
            }
        }
    ),
    datasets: List<JustOneDataset>,
) {
    vm.datasets = datasets
    LaunchedEffect(game) {
        vm.game = game
        vm.initNewRound()
    }

    Column (
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =
            if (vm.phase == JustOnePhase.SHOW_DETECTIVE)
                Arrangement.Center
            else
                Arrangement.Top,
    ) {
        key(vm.phase, vm.activePlayer) {
            if(vm.phase == JustOnePhase.SHOW_DETECTIVE) {
                ShowText(
                    word = stringResource(Res.string.just_one_res_is_detective).replace("{player}", vm.detective),
                    buttonText = stringResource(Res.string.accept_dialog),
                    callback = {
                        vm.phase = JustOnePhase.PROVIDE_HINT
                        vm.updateActivePlayer()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp,
                        ),
                )
            } else if (vm.phase == JustOnePhase.PROVIDE_HINT && vm.activePlayer != null) {
                AskHintCard(
                    word = vm.currentWord!![local],
                    player = vm.activePlayer!!,
                    onAnswer = {
                        vm.onProvideHint(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp,
                        ),
                )
            } else if (vm.phase == JustOnePhase.SHOW_HINTS) {
                ShowHints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    answers = vm.hints,
                    question = stringResource(Res.string.just_one_res_guess).replace("{player}", vm.detective),
                    showWordCallback = {
                        vm.phase = JustOnePhase.SHOW_WORD
                    },
                    buttonText = stringResource(Res.string.just_one_res_show_word),
                )
            } else if (vm.phase == JustOnePhase.SHOW_WORD) {
                ShowHints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    answers = vm.hints,
                    question = vm.currentWord!![local],
                    showWordCallback = {
                        vm.initNewRound()
                    },
                    buttonText = stringResource(Res.string.new_round),
                )
            }
        }
    }
}

@Composable
fun AskHintCard(
    modifier: Modifier = Modifier,
    word: String,
    player: String,
    onAnswer: (String) -> Unit,
) {
    var answer by remember { mutableStateOf("") }
    var opened by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
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
                    text = word,
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
                    label = {
                        Text(stringResource(Res.string.just_one_res_hint))
                    }
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    onClick = {
                        onAnswer(answer)
                    }
                ) {
                    Text(stringResource(Res.string.just_one_res_submit))
                }
            }
        }
    }
}

@Composable
fun ShowHints(
    modifier: Modifier = Modifier,
    answers: Map<String, String>,
    question: String,
    showWordCallback: () -> Unit = {},
    buttonText: String,
) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            text = question,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .weight(1f)
        ) {
            items(answers.entries.toList()) { (player, answer) ->
                val defaultColor = MaterialTheme.colorScheme.outline
                val border by remember {
                    derivedStateOf {
                        BorderStroke(1.dp, defaultColor)
                    }
                }

                val colors = CardDefaults.cardColors()
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(125.dp),
                    border = border,
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
            onClick = showWordCallback
        ) {
            Text(
                text = buttonText,
            )
        }
    }
}

@Composable
fun ShowText(
    word: String,
    buttonText: String,
    callback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = word,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                onClick = callback
            ) {
                Text(
                    text = buttonText
                )
            }
        }
    }
}
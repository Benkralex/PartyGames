package de.benkralex.partygames.games.impostor.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.benkralex.partygames.app.activeGame
import de.benkralex.partygames.app.getGameByKey
import de.benkralex.partygames.games.impostor.domain.Impostor
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

@Composable
fun ImpostorPlayWidget(
    modifier: Modifier = Modifier,
    viewModel: ImpostorPlayViewModel = viewModel<ImpostorPlayViewModel>(key = activeGame),
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
    if (game !is Impostor) {
        Napier.e("Game with key $activeGame is not a Impostor game")
        Text("Game with key $activeGame is not a Impostor game")
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
        verticalArrangement = Arrangement.Center,
    ) {
        key(viewModel.game, viewModel.activePlayer, viewModel.word) {
            if (viewModel.game != null && viewModel.word != null && viewModel.activePlayer != null) {
                ShowWordCard(
                    word = viewModel.word!![Locale.current.language + "_" + Locale.current.region],
                    player = viewModel.activePlayer!!,
                    next = {
                        viewModel.updateActivePlayer()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp,
                        ),
                    impostor = viewModel.impostors.contains(viewModel.activePlayer),
                )
            } else if (viewModel.game != null && viewModel.activePlayer == null && viewModel.finishedPlayers.size == viewModel.playerCount) {
                ShowImpostors(
                    mainWord = viewModel.currentWordPair!!.mainWord[Locale.current.language + "_" + Locale.current.region],
                    impostors = viewModel.impostors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((min(max(200 + viewModel.impostors.size * 40, 300), 700)).dp)
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp,
                        ),
                    newRoundCallback = {
                        viewModel.initNewRound()
                    },
                )
            }
        }
    }
}

@Composable
fun ShowWordCard(
    modifier: Modifier = Modifier,
    word: String,
    impostor: Boolean,
    player: String,
    next: () -> Unit = {},
) {
    var opened by remember { mutableStateOf(false) }
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
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = if (!impostor) {
                            word
                        } else {
                            "Impostor"
                        },
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    if (impostor) {
                        Text(
                            text = word,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    onClick = {
                        next()
                    }
                ) {
                    Text("Fertig")
                }
            }
        }
    }
}

@Composable
fun ShowImpostors(
    modifier: Modifier = Modifier,
    mainWord: String,
    impostors: List<String>,
    newRoundCallback: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val isScrollable = scrollState.maxValue > 0
    var opened by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Card (
        modifier = if (!opened) {
            modifier
                .padding(8.dp)
                .clickable { opened = true }
        } else {
            modifier.padding(8.dp)
       },
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
                    text = "Aufdecken",
                    style = MaterialTheme.typography.displayMedium,
                )
            } else {
                Text(
                    text = "Wort: $mainWord",
                    style = MaterialTheme.typography.headlineLarge,
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        impostors.forEach {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                    if (isScrollable) {
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp),
                            onClick = {
                                Napier.e("Scrolling to top")
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(
                                        0
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowUpward,
                                contentDescription = "scroll up",
                            )
                        }
                    }
                    if (isScrollable) {
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp),
                            onClick = {
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(
                                        scrollState.maxValue
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDownward,
                                contentDescription = "scroll down",
                            )
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    onClick = {
                        opened = false
                        newRoundCallback()
                    }
                ) {
                    Text("Neue Runde")
                }
            }
        }
    }
}
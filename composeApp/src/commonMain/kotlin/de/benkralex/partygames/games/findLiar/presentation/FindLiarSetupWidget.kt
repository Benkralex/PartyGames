package de.benkralex.partygames.games.findLiar.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.presentation.CheckboxInputWidget
import de.benkralex.partygames.games.common.presentation.SelectDifficultyWidget
import de.benkralex.partygames.games.common.presentation.SelectNumberWidget
import de.benkralex.partygames.games.common.presentation.StringListInputWidget
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.find_liar_res_liar_count
import partygames.composeapp.generated.resources.find_liar_res_player
import partygames.composeapp.generated.resources.find_liar_res_player_name
import partygames.composeapp.generated.resources.find_liar_res_players
import partygames.composeapp.generated.resources.start_game
import partygames.composeapp.generated.resources.topics

@Composable
fun FindLiarSetupWidget(
    modifier: Modifier = Modifier,
    setupGame: (List<String>, Int, List<String>, Difficulty) -> Unit = { _, _, _, _ -> }
) {
    /*
        players: List<String>,
        liarCount: Int,
        topics: List<String>,
        difficulty: Difficulty,
    */
    val scrollState = rememberScrollState()
    Column (
        modifier = modifier.verticalScroll(scrollState),
    ) {
        var players = mutableListOf(
            "${stringResource(Res.string.find_liar_res_player)} 1",
            "${stringResource(Res.string.find_liar_res_player)} 2",
            "${stringResource(Res.string.find_liar_res_player)} 3",
        )
        val playerCount = remember { mutableStateOf(3) }
        val liarCount = mutableStateOf(1)
        val topics = emptyList<String>().toMutableList()
        val difficulty = remember { mutableStateOf(Difficulty.MEDIUM) }

        val childModifier: Modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .fillMaxWidth()
        StringListInputWidget(
            label = stringResource(Res.string.find_liar_res_players),
            initialValue = players.toList(),
            onValueChange = { newPlayers ->
                playerCount.value = newPlayers.size
                players = newPlayers.toMutableList()
            },
            modifier = childModifier,
            minCount = 3,
            textFieldLabel = stringResource(Res.string.find_liar_res_player_name),
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
        )
        SelectNumberWidget(
            label = stringResource(Res.string.find_liar_res_liar_count),
            min = 1,
            max = playerCount.value - 1,
            onNumberSelected = { newLiarCount ->
                liarCount.value = newLiarCount
            },
            modifier = childModifier,
            initialValue = liarCount.value.toString(),
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
        )
        SelectDifficultyWidget(
            onDifficultySelected = { newDifficulty ->
                difficulty.value = newDifficulty
            },
            modifier = childModifier,
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
        )
        topics.clear()
        topics.addAll(
            listOf(
                "Lorem ipsum1",
                "Lorem ipsum4",
                "Lorem ipsum5",
            )
        )
        CheckboxInputWidget(
            modifier = childModifier,
            label = stringResource(Res.string.topics),
            options = mapOf(
                "Lorem ipsum1 " to true,
                "Lorem ipsum2" to false,
                "Lorem ipsum3" to false,
                "Lorem ipsum4" to true,
                "Lorem ipsum5" to true,
            ),
            onValueChange = { newTopics ->
                topics.clear()
                topics.addAll(newTopics.filter { it.value }.keys)
            },
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
        )
        ElevatedButton(
            onClick = {
                setupGame(
                    players,
                    liarCount.value,
                    topics,
                    difficulty.value
                )
            },
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .align(Alignment.End),
            colors = ButtonDefaults.filledTonalButtonColors(),
        ) {
            Text(
                text = stringResource(Res.string.start_game),
            )
        }
        Spacer(modifier = Modifier.height(70.dp))
    }
}
package de.benkralex.partygames.games.findLiar.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.presentation.SelectBooleanWidget
import de.benkralex.partygames.games.common.presentation.SelectDifficultyWidget
import de.benkralex.partygames.games.common.presentation.SelectNumberWidget
import de.benkralex.partygames.games.common.presentation.StringListInputWidget
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.find_liar_res_liar_count
import partygames.composeapp.generated.resources.find_liar_res_player
import partygames.composeapp.generated.resources.find_liar_res_player_name
import partygames.composeapp.generated.resources.find_liar_res_players

@Composable
fun FindLiarSetupWidget(
    modifier: Modifier = Modifier,
) {
    /*
        players: List<String>,
        liarCount: Int,
        topics: List<String>,
        difficulty: Difficulty,
    */
    Column (
        modifier = modifier,
    ) {
        val playerCount = remember { mutableStateOf(3) }
        val childModifier: Modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .fillMaxWidth()
        StringListInputWidget(
            label = stringResource(Res.string.find_liar_res_players),
            initialValue = listOf(
                "${stringResource(Res.string.find_liar_res_player)} 1",
                "${stringResource(Res.string.find_liar_res_player)} 2",
                "${stringResource(Res.string.find_liar_res_player)} 3",
            ),
            onValueChange = { players ->
                playerCount.value = players.size
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
            onNumberSelected = { liarCount ->
                // Handle liar count selection
            },
            modifier = childModifier,
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
        )
        SelectDifficultyWidget(
            onDifficultySelected = { difficulty ->
                // Handle difficulty selection
            },
            modifier = childModifier,
        )
    }
}
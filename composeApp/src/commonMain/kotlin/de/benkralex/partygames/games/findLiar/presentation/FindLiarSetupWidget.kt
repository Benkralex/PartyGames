package de.benkralex.partygames.games.findLiar.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import partygames.composeapp.generated.resources.topics

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
    val scrollState = rememberScrollState()
    Column (
        modifier = modifier.verticalScroll(scrollState),
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
            initialValue = "1",
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
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
        )
        CheckboxInputWidget(
            modifier = childModifier,
            label = stringResource(Res.string.topics),
            options = mapOf(
                "Animals" to true,
                "Food" to false,
                "Movies" to false,
                "Books" to false,
                "Sports" to false,
                "Music" to false,
            ),
            onValueChange = {  },
        )
        Spacer(modifier = Modifier.height(50.dp))
    }
}
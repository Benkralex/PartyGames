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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput.CheckboxListWidget
import de.benkralex.partygames.games.common.presentation.setupWidgets.difficultyInput.DifficultyInputWidget
import de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput.IntegerInputWidget
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringListWidget
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.difficulty
import partygames.composeapp.generated.resources.find_liar_res_liar_count
import partygames.composeapp.generated.resources.find_liar_res_player
import partygames.composeapp.generated.resources.find_liar_res_player_name
import partygames.composeapp.generated.resources.find_liar_res_players
import partygames.composeapp.generated.resources.start_game
import partygames.composeapp.generated.resources.topics

@Composable
fun FindLiarSetupWidget(
    modifier: Modifier = Modifier,
    viewModel: FindLiarSetupViewModel = viewModel<FindLiarSetupViewModel>(),
    setupGame: (List<String>, Int, List<String>, Difficulty) -> Unit = { _, _, _, _ -> }
) {
    // initialise labels
    val playerListLabel = stringResource(Res.string.find_liar_res_players)
    val playerSingleLabel = stringResource(Res.string.find_liar_res_player_name)
    val playerNameStart = stringResource(Res.string.find_liar_res_player)
    val liarCountLabel = stringResource(Res.string.find_liar_res_liar_count)
    val difficultyLabel = stringResource(Res.string.difficulty)
    val topicsLabel = stringResource(Res.string.topics)

    viewModel.initializeLabels(
        playerListLabel, playerSingleLabel, playerNameStart,
        liarCountLabel, difficultyLabel, topicsLabel
    )

    // update Liar Count Constraints
    viewModel.updateLiarCountConstraints()

    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        @Composable
        fun CustomDivider() {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
            )
        }

        val childModifier: Modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .fillMaxWidth()

        StringListWidget(modifier = childModifier, state = viewModel.playersState)
        CustomDivider()

        IntegerInputWidget(modifier = childModifier, state = viewModel.liarCountState)
        CustomDivider()

        DifficultyInputWidget(modifier = childModifier, state = viewModel.difficultyState)
        CustomDivider()

        CheckboxListWidget(modifier = childModifier, state = viewModel.topicsState)
        CustomDivider()

        ElevatedButton(
            onClick = { viewModel.setupGame(setupGame) },
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .align(Alignment.End),
            colors = ButtonDefaults.filledTonalButtonColors(),
        ) {
            Text(text = stringResource(Res.string.start_game))
        }

        Spacer(modifier = Modifier.height(70.dp))
    }
}
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput.CheckboxListState
import de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput.CheckboxListWidget
import de.benkralex.partygames.games.common.presentation.setupWidgets.checkboxInput.CheckboxSingleState
import de.benkralex.partygames.games.common.presentation.setupWidgets.difficultyInput.DifficultyInputState
import de.benkralex.partygames.games.common.presentation.setupWidgets.difficultyInput.DifficultyInputWidget
import de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput.IntegerInputState
import de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput.IntegerInputWidget
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringListState
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringListWidget
import de.benkralex.partygames.games.common.presentation.setupWidgets.stringInput.StringSingleState
import io.github.aakira.napier.Napier
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
    setupGame: (List<String>, Int, List<String>, Difficulty) -> Unit = { _, _, _, _ -> }
) {
    val scrollState = rememberScrollState()
    Column (
        modifier = modifier.verticalScroll(scrollState),
    ) {
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

        //-------------------------------
        // Players
        val playerListLabel = stringResource(Res.string.find_liar_res_players)
        val playerSingleLabel = stringResource(Res.string.find_liar_res_player_name)
        val playerNameStart = stringResource(Res.string.find_liar_res_player)

        val playersState = remember {
            StringListState(
                label = playerListLabel,
                stringSingleStates = mapOf(
                    0 to StringSingleState(
                        label = playerSingleLabel,
                        defaultValue = playerNameStart,
                    ),
                    1 to StringSingleState(
                        label = playerSingleLabel,
                        defaultValue = playerNameStart,
                    ),
                    2 to StringSingleState(
                        label = playerSingleLabel,
                        defaultValue = playerNameStart,
                    ),
                ),
                minCount = 3,
                textFieldLabel = playerSingleLabel,
                defaultValue = playerNameStart,
                noDuplicates = true,
            )
        }

        val players by remember {
            derivedStateOf {
                playersState.stringSingleStates
                    .map { it.value.value }
                    .filter { it.isNotEmpty() }
            }
        }
        val playerCount by remember {
            derivedStateOf {
                players.size
            }
        }
        StringListWidget(
            modifier = childModifier,
            state = playersState,
        )
        //-------------------------------

        CustomDivider()

        //-------------------------------
        // Liar Count
        val liarCountLabel = stringResource(Res.string.find_liar_res_liar_count)

        val liarCountState = remember {
            IntegerInputState(
                label = liarCountLabel,
                min = 1,
                max = playerCount - 1,
                defaultValue = 1,
            )
        }

        liarCountState.max = playerCount - 1
        if (liarCountState.value > liarCountState.max) {
            liarCountState.value = liarCountState.max
            liarCountState.realValue = liarCountState.max.toString()
        }
        val liarCount by remember {
            derivedStateOf {
                liarCountState.value
            }
        }
        IntegerInputWidget(
            modifier = childModifier,
            state = liarCountState,
        )
        //-------------------------------

        CustomDivider()

        //-------------------------------
        // Difficulty
        val difficultyLabel = stringResource(Res.string.difficulty)

        val difficultyState = remember {
            DifficultyInputState(
                label = difficultyLabel,
                defaultValue = Difficulty.MEDIUM,
            )
        }

        val difficulty by remember {
            derivedStateOf {
                difficultyState.value
            }
        }

        DifficultyInputWidget(
            modifier = childModifier,
            state = difficultyState,
        )
        //-------------------------------

        CustomDivider()

        //-------------------------------
        // Topics
        val topicsLabel = stringResource(Res.string.topics)

        val topicsState = remember {
            CheckboxListState(
                label = topicsLabel,
                checkboxSingleStates = listOf(
                    CheckboxSingleState("Lorem ipsum1", true),
                    CheckboxSingleState("Lorem ipsum2", false),
                    CheckboxSingleState("Lorem ipsum3", false),
                    CheckboxSingleState("Lorem ipsum4", true),
                    CheckboxSingleState("Lorem ipsum5", true),
                ),
                minCount = 1,
            )
        }

        val topics by remember {
            derivedStateOf {
                topicsState.checkboxSingleStates
                    .filter { it.value }
                    .map { it.label }
            }
        }

        CheckboxListWidget(
            modifier = childModifier,
            state = topicsState,
        )
        //-------------------------------

        CustomDivider()

        //-------------------------------
        // Start Game Button
        ElevatedButton(
            onClick = {
                Napier.i(
                    "players=$players," +
                            " liarCount=${liarCount}," +
                            " topics=$topics," +
                            " difficulty=${difficulty}"
                )
                if (
                    playersState.stringSingleStates.values.map { it.isError }.any( predicate = {it} ) ||
                    liarCountState.isError ||
                    difficultyState.isError ||
                    topicsState.isError
                ) {
                    Napier.e("Setup is invalid, cannot start game: " +
                            "playersState=${playersState.stringSingleStates.values.map { it.isError }.any( predicate = {it} )}," +
                            " liarCountState=${liarCountState.isError}," +
                            " difficultyState=${difficultyState.isError}," +
                            " topicsState=${topicsState.isError}")
                    return@ElevatedButton
                }
                setupGame(
                    players,
                    liarCount,
                    topics,
                    difficulty
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
        //-------------------------------

        Spacer(modifier = Modifier.height(70.dp))
    }
}
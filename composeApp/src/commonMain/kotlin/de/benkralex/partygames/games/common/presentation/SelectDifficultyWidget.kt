package de.benkralex.partygames.games.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.benkralex.partygames.games.common.domain.Difficulty
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.difficulty

@Composable
fun SelectDifficultyWidget(
    modifier: Modifier = Modifier,
    onDifficultySelected: (Difficulty) -> Unit,
) {
    SelectNumberWidget(
        modifier = modifier,
        onNumberSelected = { number ->
            onDifficultySelected(
                when (number) {
                    1 -> Difficulty.TRIVIAL
                    2 -> Difficulty.EXTRA_EASY
                    3 -> Difficulty.EASY
                    4 -> Difficulty.MODERATE
                    5 -> Difficulty.MEDIUM
                    6 -> Difficulty.CHALLENGING
                    7 -> Difficulty.HARD
                    8 -> Difficulty.VERY_HARD
                    9 -> Difficulty.EXTREME
                    10 -> Difficulty.NIGHTMARE
                    else -> Difficulty.MODERATE
                }
            )
        },
        min = 1,
        max = 10,
        label = "${stringResource(Res.string.difficulty)} (1-10)",
    )
}
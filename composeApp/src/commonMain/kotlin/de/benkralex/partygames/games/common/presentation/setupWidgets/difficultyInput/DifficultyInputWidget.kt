package de.benkralex.partygames.games.common.presentation.setupWidgets.difficultyInput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput.IntegerInputState
import de.benkralex.partygames.games.common.presentation.setupWidgets.integerInput.IntegerInputWidget
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.difficulty_challenging
import partygames.composeapp.generated.resources.difficulty_easy
import partygames.composeapp.generated.resources.difficulty_extra_easy
import partygames.composeapp.generated.resources.difficulty_extreme
import partygames.composeapp.generated.resources.difficulty_hard
import partygames.composeapp.generated.resources.difficulty_medium
import partygames.composeapp.generated.resources.difficulty_moderate
import partygames.composeapp.generated.resources.difficulty_nightmare
import partygames.composeapp.generated.resources.difficulty_trivial
import partygames.composeapp.generated.resources.difficulty_very_hard

@Composable
fun DifficultyInputWidget(
    modifier: Modifier = Modifier,
    state: DifficultyInputState
) {
    val difficultyNames: Map<Difficulty, StringResource> = mapOf(
        Difficulty.TRIVIAL to Res.string.difficulty_trivial,
        Difficulty.EXTRA_EASY to Res.string.difficulty_extra_easy,
        Difficulty.EASY to Res.string.difficulty_easy,
        Difficulty.MODERATE to Res.string.difficulty_moderate,
        Difficulty.MEDIUM to Res.string.difficulty_medium,
        Difficulty.CHALLENGING to Res.string.difficulty_challenging,
        Difficulty.HARD to Res.string.difficulty_hard,
        Difficulty.VERY_HARD to Res.string.difficulty_very_hard,
        Difficulty.EXTREME to Res.string.difficulty_extreme,
        Difficulty.NIGHTMARE to Res.string.difficulty_nightmare,
    )

    val numberInputState = remember {
        IntegerInputState(
            label = state.label,
            min = 1,
            max = 10,
            defaultValue = 5,
        )
    }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val difficulty by remember {
            derivedStateOf {
                difficultyFromNumber(numberInputState.value)
            }
        }
        state.value = difficulty
        IntegerInputWidget(
            state = numberInputState,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = stringResource(
                resource = difficultyNames.getOrElse(
                    key = state.value,
                    defaultValue = { Res.string.difficulty_medium },
                ),
            ),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

private fun difficultyFromNumber(number: Int): Difficulty {
    return when (number) {
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
        else -> throw IllegalArgumentException("Invalid difficulty number: $number")
    }
}
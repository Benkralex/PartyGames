package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import de.benkralex.partygames.games.common.domain.Difficulty

@Composable
fun SelectDifficultyWidget(
    modifier: Modifier = Modifier,
    onDifficultySelected: (Difficulty) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = "",
        onValueChange = { newValue ->
            val number = newValue.toIntOrNull()
            if (number != null && number in 1..10) {
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
            }
        },
        placeholder = {
            Text(
                text = "Difficulty (1-10)",
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
}
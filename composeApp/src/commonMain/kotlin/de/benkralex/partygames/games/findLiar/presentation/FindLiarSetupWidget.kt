package de.benkralex.partygames.games.findLiar.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.benkralex.partygames.games.common.presentation.SelectDifficultyWidget
import de.benkralex.partygames.games.common.presentation.SelectNumberWidget

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
        SelectNumberWidget(
            placeholder = "liar count",
            min = 1,
            max = 5,
            onNumberSelected = { liarCount ->
                // Handle liar count selection
            }
        )
        SelectDifficultyWidget(
            onDifficultySelected = { difficulty ->
                // Handle difficulty selection
            }
        )
    }
}
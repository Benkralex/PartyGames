package de.benkralex.partygames.gameSelection.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.presentation.GameSelectionCard
import de.benkralex.partygames.games.findLiar.data.FindLiar
import de.benkralex.partygames.games.impostor.data.Impostor
import de.benkralex.partygames.games.truthOrDare.data.TruthOrDare

@Composable
fun GameSelectionList (
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .padding(16.dp),
    ) {
        GameSelectionCard(
            game = Impostor(
                playerCount =0,
                impostorCount = 0,
                topics = listOf(),
                hint = false,
                difficulty = Difficulty.MEDIUM,
            )
        )
        Box(
            modifier = Modifier
                .height(8.dp)
        )
        GameSelectionCard(
            game = TruthOrDare(
                playerCount =0,
                topics = listOf(),
                difficulty = Difficulty.MEDIUM,
            )
        )
        Box(
            modifier = Modifier
                .height(8.dp)
        )
        GameSelectionCard(
            game = FindLiar(
                playerCount =0,
                liarCount = 0,
                topics = listOf(),
                difficulty = Difficulty.MEDIUM,
            )
        )
        Box(
            modifier = Modifier
                .height(8.dp)
        )
    }
}
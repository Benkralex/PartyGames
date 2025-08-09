package de.benkralex.partygames.gameSelectionPage.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.findLiar.domain.FindLiar
import de.benkralex.partygames.games.impostor.domain.Impostor
import de.benkralex.partygames.games.truthOrDare.domain.TruthOrDare

@Composable
fun GameSelectionPage(
    onNavigateToSettings: () -> Unit = {},
    onNavigateToGame: (Game) -> Unit = {},
) {
    Scaffold (
        topBar = {
            GameSelectionTopAppBar(
                onSettingsClick = onNavigateToSettings
            )
        }
    ) {
        innerPadding ->
        GameSelectionList(
            onGameClick = onNavigateToGame,
            modifier = Modifier
                .padding(innerPadding),
            games = listOf(
                Impostor(),
                TruthOrDare(),
                FindLiar(),
            ),
        )
    }
}
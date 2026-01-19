package de.benkralex.partygames.gameSelectionPage.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.benkralex.partygames.app.gamesRegister
import de.benkralex.partygames.games.common.domain.Game

/**
 * Display a game selection screen with a top app bar and a list of registered games.
 *
 * @param onNavigateToSettings Invoked when the settings action is triggered.
 * @param onNavigateToGame Invoked with the selected `Game` when a game is chosen from the list.
 */
@Composable
fun GameSelectionPage(
    onNavigateToSettings: () -> Unit = {},
    onNavigateToGame: (Game<*,*>) -> Unit = {},
) {
    Scaffold (
        topBar = {
            GameSelectionTopAppBar(
                onSettingsClick = onNavigateToSettings
            )
        }
    ) { innerPadding ->
        GameSelectionList(
            onGameClick = onNavigateToGame,
            modifier = Modifier
                .padding(innerPadding),
            games = gamesRegister,
        )
    }
}
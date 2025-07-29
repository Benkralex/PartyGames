package de.benkralex.partygames.gameSelection.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.benkralex.partygames.core.domain.Page
import de.benkralex.partygames.games.findLiar.data.FindLiar
import de.benkralex.partygames.games.impostor.data.Impostor
import de.benkralex.partygames.games.truthOrDare.data.TruthOrDare

@Composable
fun GameSelectionPage(
    onNavigateTo: (Page) -> Unit = {},
) {
    Scaffold (
        topBar = {
            GameSelectionTopAppBar(
                onSettingsClick = {
                    // Handle settings click
                }
            )
        }
    ) {
        innerPadding ->
        GameSelectionList(
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
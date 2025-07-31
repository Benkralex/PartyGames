package de.benkralex.partygames.gameSelectionPage.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.presentation.GameSelectionCard

@Composable
fun GameSelectionList (
    modifier: Modifier = Modifier,
    games: List<Game>,
    onGameClick: (Game) -> Unit = {},
) {
    val scrollState: LazyListState = rememberLazyListState()
    LazyColumn (
        modifier = modifier
            .padding(16.dp),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items (
            items = games,
            key = { game -> game.information.name.key }
        ) { game ->
            GameSelectionCard(
                modifier = Modifier
                    .clickable(
                        onClick = { onGameClick(game) },
                    ),
                game = game,
            )
        }
    }
}
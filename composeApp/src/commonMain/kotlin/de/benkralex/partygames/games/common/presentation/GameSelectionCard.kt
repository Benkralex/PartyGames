package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Game
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameSelectionCard(
    game: Game,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = game.information.color,
                shape = MaterialTheme.shapes.small,
            )
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = stringResource(game.information.name),
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = stringResource(game.information.author),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = stringResource(game.information.description),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
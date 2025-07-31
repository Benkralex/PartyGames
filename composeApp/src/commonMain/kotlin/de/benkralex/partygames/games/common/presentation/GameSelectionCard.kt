package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Game
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameSelectionCard(
    game: Game,
    modifier: Modifier = Modifier,
) {
    val color = if (isSystemInDarkTheme()) {
        game.information.colorDarkTheme
    } else {
        game.information.colorLightTheme
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(16.dp),
            )
            .drawBehind {
                drawRoundRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            color.copy(alpha = 0.5f),
                            color.copy(alpha = 0.1f)
                        ),
                        center = Offset(size.width,0f),
                        radius = size.maxDimension / 1.2f
                    ),
                    size = size,
                    cornerRadius = CornerRadius(
                        x = 16.dp.toPx(),
                        y = 16.dp.toPx()
                    )
                )
            }
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
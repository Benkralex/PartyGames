package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Game
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameSelectionCard(
    game: Game<*,*>,
    modifier: Modifier = Modifier,
) {
    val color = if (isSystemInDarkTheme()) {
        game.information.colorDarkTheme
    } else {
        game.information.colorLightTheme
    }
    Box(
        modifier = if (isSystemInDarkTheme()) {
            modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(16.dp),
                )
                .background(
                    color = color,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(16.dp)
        } else {
            modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(16.dp),
                )
                .dropShadow(
                    shape = RoundedCornerShape(16.dp),
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                        radius = 6.dp,
                        spread = 2.dp,
                        offset = DpOffset(2.dp, 2.dp)
                    ),
                )
                .background(
                    color = color,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(16.dp)
        }
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
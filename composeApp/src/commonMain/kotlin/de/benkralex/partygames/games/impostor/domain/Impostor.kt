package de.benkralex.partygames.games.impostor.domain

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.app.activeGame
import de.benkralex.partygames.app.getKeyByGame
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import de.benkralex.partygames.games.impostor.presentation.ImpostorPlayWidget
import de.benkralex.partygames.games.impostor.presentation.ImpostorSetupWidget
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.impostor_author
import partygames.composeapp.generated.resources.impostor_description
import partygames.composeapp.generated.resources.impostor_how_to_play
import partygames.composeapp.generated.resources.impostor_title

class Impostor : Game {
    override val information: GameInformation = GameInformation(
        name = Res.string.impostor_title,
        description = Res.string.impostor_description,
        author = Res.string.impostor_author,
        colorLightTheme = Color.Blue,
        colorDarkTheme = Color.Blue,
        howToPlay = Res.string.impostor_how_to_play,
    )

    override var settings: Map<String, Any?> = mapOf(
        "players" to null,
        "impostorCount" to null,
        "topics" to null,
        "hint" to null,
        "difficulty" to null,
    )

    override val setupWidget = @Composable { modifier: Modifier ->
        ImpostorSetupWidget(
            modifier = modifier,
            setupGame = { players, impostorCount, topics, difficulty ->
                createGame(
                    settings = mapOf(
                        "players" to players,
                        "impostorCount" to impostorCount,
                        "topics" to topics,
                        "difficulty" to difficulty
                    ),
                )
            }
        )
    }

    override val playWidget = @Composable { modifier: Modifier ->
        ImpostorPlayWidget(
            modifier = modifier,
        )
    }

    override val settingsWidget = @Composable { modifier: Modifier ->
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${stringResource(information.name)} Settings",
            )
        }
    }

    override fun createGame(
        settings: Map<String, Any?>
    ) {
        this.settings = settings
        activeGame = getKeyByGame(this)
    }
}
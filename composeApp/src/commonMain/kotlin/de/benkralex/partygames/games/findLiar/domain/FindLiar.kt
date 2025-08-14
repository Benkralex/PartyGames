package de.benkralex.partygames.games.findLiar.domain

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.app.activeGame
import de.benkralex.partygames.app.getKeyByGame
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import de.benkralex.partygames.games.findLiar.presentation.FindLiarPlayWidget
import de.benkralex.partygames.games.findLiar.presentation.FindLiarSetupWidget
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.find_liar_author
import partygames.composeapp.generated.resources.find_liar_description
import partygames.composeapp.generated.resources.find_liar_how_to_play
import partygames.composeapp.generated.resources.find_liar_title

class FindLiar : Game {
    override val information: GameInformation = GameInformation(
        name = Res.string.find_liar_title,
        description = Res.string.find_liar_description,
        author = Res.string.find_liar_author,
        colorLightTheme = Color.Companion.Red,
        colorDarkTheme = Color.Companion.Red,
        howToPlay = Res.string.find_liar_how_to_play,
    )

    override var settings: Map<String, Any?> = mapOf(
        "players" to null,
        "liarCount" to null,
        "topics" to null,
        "difficulty" to null,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override val setupWidget = @Composable { modifier: Modifier ->
        FindLiarSetupWidget(
            modifier = modifier,
            setupGame = { players, liarCount, topics, difficulty ->
                createGame(
                    mapOf(
                        "players" to players,
                        "liarCount" to liarCount,
                        "topics" to topics,
                        "difficulty" to difficulty,
                    )
                )
            }
        )
    }

    override val playWidget = @Composable { modifier: Modifier ->
        FindLiarPlayWidget(
            modifier = modifier,
            game = this,
        )
    }

    override val settingsWidget = null

    override fun createGame(
        settings: Map<String, Any?>,
    ) {
        this.settings = settings
        activeGame = getKeyByGame(this)
    }
}
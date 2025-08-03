package de.benkralex.partygames.games.impostor.data

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import partygames.composeapp.generated.resources.*

class Impostor : Game {
    override val information: GameInformation = GameInformation(
        name = Res.string.impostor_title,
        description = Res.string.impostor_description,
        author = Res.string.impostor_author,
        image = "impostor_image",
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
        Text("Test I", modifier = modifier)
    }
    override val playWidget: @Composable ((Modifier) -> Unit)
        get() = {}

    override fun createGame(
        /*players: List<String>,
        impostorCount: Int,
        topics: List<String>,
        hint: Boolean,
        difficulty: Difficulty,*/
        settings: Map<String, Any?>
    ) {
        this.settings = settings /*mapOf(
            "players" to players,
            "impostorCount" to impostorCount,
            "topics" to topics,
            "hint" to hint,
            "difficulty" to difficulty,
        )*/
    }
}

data class ImpostorWordPair (
    val mainWord: String,
    val impostorHint: String,
)
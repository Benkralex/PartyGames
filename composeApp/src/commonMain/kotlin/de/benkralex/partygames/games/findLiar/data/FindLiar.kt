package de.benkralex.partygames.games.findLiar.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import de.benkralex.partygames.games.findLiar.presentation.FindLiarSetupWidget
import kotlinx.serialization.Serializable
import partygames.composeapp.generated.resources.*

class FindLiar : Game {
    override val information: GameInformation = GameInformation(
        name = Res.string.find_liar_title,
        description = Res.string.find_liar_description,
        author = Res.string.find_liar_author,
        image = "find_liar_image",
        colorLightTheme = Color.Red,
        colorDarkTheme = Color.Red,
        howToPlay = Res.string.find_liar_how_to_play,
    )

    override var settings: Map<String, Any?> = mapOf(
        "players" to null,
        "liarCount" to null,
        "topics" to null,
        "difficulty" to null,
    )

    override val setupWidget = @Composable { modifier: Modifier ->
        FindLiarSetupWidget(modifier = modifier)
    }

    fun createGame(
        players: List<String>,
        liarCount: Int,
        topics: List<String>,
        difficulty: Difficulty,
    ) {
        settings = mapOf(
            "players" to players,
            "liarCount" to liarCount,
            "topics" to topics,
            "difficulty" to difficulty,
        )
    }
}

@Serializable
data class FindLiarQuestionPair(
    val mainQuestion: String,
    val liarQuestion: String,
)
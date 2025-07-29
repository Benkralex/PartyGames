package de.benkralex.partygames.games.impostor.data

import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.impostor_author
import partygames.composeapp.generated.resources.impostor_description
import partygames.composeapp.generated.resources.impostor_title

class Impostor : Game {
    override val information: GameInformation = GameInformation(
        name = Res.string.impostor_title,
        description = Res.string.impostor_description,
        author = Res.string.impostor_author,
        image = "impostor_image",
        color = Color.Blue,
    )

    override var settings: Map<String, Any?> = mapOf(
        "playerCount" to null,
        "impostorCount" to null,
        "topics" to null,
        "hint" to null,
        "difficulty" to null,
    )

    fun createGame(
        playerCount: Int,
        impostorCount: Int,
        topics: List<String>,
        hint: Boolean,
        difficulty: Difficulty,
    ) {
        settings = mapOf(
            "playerCount" to playerCount,
            "impostorCount" to impostorCount,
            "topics" to topics,
            "hint" to hint,
            "difficulty" to difficulty,
        )
    }
}

data class ImpostorWordPair (
    val mainWord: String,
    val impostorHint: String,
)
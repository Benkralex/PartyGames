package de.benkralex.partygames.games.impostor.data

import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.impostor_author
import partygames.composeapp.generated.resources.impostor_description
import partygames.composeapp.generated.resources.impostor_title

class Impostor(
    playerCount: Int,
    impostorCount: Int,
    topics: List<String>,
    hint: Boolean,
    difficulty: Difficulty,
    override val generateContentWithAI: Boolean = false,
) : Game {
    override val information: GameInformation = GameInformation(
        name = Res.string.impostor_title,
        description = Res.string.impostor_description,
        author = Res.string.impostor_author,
        image = "impostor_image",
        color = Color.Blue,
    )


    override val settings: Map<String, Any> = mapOf(
        "playerCount" to playerCount,
        "impostorCount" to impostorCount,
        "topics" to topics,
        "hint" to hint,
        "difficulty" to difficulty,
    )


    override val prompt: String = ""
    override val argsNames: List<String> = listOf()
}
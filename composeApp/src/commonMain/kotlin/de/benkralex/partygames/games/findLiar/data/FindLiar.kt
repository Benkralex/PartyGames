package de.benkralex.partygames.games.findLiar.data

import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.find_liar_author
import partygames.composeapp.generated.resources.find_liar_description
import partygames.composeapp.generated.resources.find_liar_title
import partygames.composeapp.generated.resources.impostor_title


class FindLiar(
    playerCount: Int,
    liarCount: Int,
    topics: List<String>,
    difficulty: Difficulty,
    override val generateContentWithAI: Boolean = false,
) : Game {
    override val information: GameInformation = GameInformation(
        name = Res.string.find_liar_title,
        description = Res.string.find_liar_description,
        author = Res.string.find_liar_author,
        image = "find_liar_image",
        color = Color.Red,
    )


    override val settings: Map<String, Any> = mapOf(
        "playerCount" to playerCount,
        "liarCount" to liarCount,
        "topics" to topics,
        "difficulty" to difficulty,
    )


    override val prompt: String = ""
    override val argsNames: List<String> = listOf()
}
package de.benkralex.partygames.games.truthOrDare.data

import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.truth_or_dare_author
import partygames.composeapp.generated.resources.truth_or_dare_description
import partygames.composeapp.generated.resources.truth_or_dare_title

class TruthOrDare(
    playerCount: Int,
    topics: List<String>,
    difficulty: Difficulty,
    override val generateContentWithAI: Boolean = false,
) : Game {
    override var information: GameInformation = GameInformation(
        name = Res.string.truth_or_dare_title,
        description = Res.string.truth_or_dare_description,
        author = Res.string.truth_or_dare_author,
        image = "truth_or_dare_image",
        color = Color.Green,
    )


    override val settings: Map<String, Any> = mapOf(
        "playerCount" to playerCount,
        "topics" to topics,
        "difficulty" to difficulty,
    )


    override val prompt: String = ""
    override val argsNames: List<String> = listOf()
}
package de.benkralex.partygames.games.truthOrDare.data

import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.truth_or_dare_author
import partygames.composeapp.generated.resources.truth_or_dare_description
import partygames.composeapp.generated.resources.truth_or_dare_title

class TruthOrDare : Game {
    override var information: GameInformation = GameInformation(
        name = Res.string.truth_or_dare_title,
        description = Res.string.truth_or_dare_description,
        author = Res.string.truth_or_dare_author,
        image = "truth_or_dare_image",
        color = Color.Green,
    )

    override var settings: Map<String, Any?> = mapOf(
        "topics" to null,
        "ageMin" to null,
        "ageMax" to null,
    )

    fun createGame(
        topics: List<String>,
        ageMin: Int?,
        ageMax: Int?,
    ) {
        settings = mapOf(
            "topics" to topics,
            "ageMin" to ageMin,
            "ageMax" to ageMax,
        )
    }
}
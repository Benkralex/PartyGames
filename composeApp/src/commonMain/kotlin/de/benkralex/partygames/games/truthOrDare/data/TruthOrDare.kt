package de.benkralex.partygames.games.truthOrDare.data

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.truth_or_dare_author
import partygames.composeapp.generated.resources.truth_or_dare_description
import partygames.composeapp.generated.resources.truth_or_dare_how_to_play
import partygames.composeapp.generated.resources.truth_or_dare_title

class TruthOrDare : Game {
    override var information: GameInformation = GameInformation(
        name = Res.string.truth_or_dare_title,
        description = Res.string.truth_or_dare_description,
        author = Res.string.truth_or_dare_author,
        image = "truth_or_dare_image",
        colorLightTheme = Color.Green,
        colorDarkTheme = Color.Green,
        howToPlay = Res.string.truth_or_dare_how_to_play,
    )

    override var settings: Map<String, Any?> = mapOf(
        "topics" to null,
        "ageMin" to null,
        "ageMax" to null,
    )

    override val setupWidget = @Composable { modifier: Modifier ->
        Text("Test TOD", modifier = modifier)
    }
    override val playWidget: @Composable ((Modifier) -> Unit)
        get() = {}

    override fun createGame(
        //topics: List<String>,
        //ageMin: Int?,
        //ageMax: Int?,
        settings: Map<String, Any?>
    ) {
        this.settings = settings// mapOf(
            //"topics" to topics,
            //"ageMin" to ageMin,
            //"ageMax" to ageMax,
        //)
    }
}
package de.benkralex.partygames.games.truthOrDare.domain

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import kotlinx.serialization.json.JsonObject
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.truth_or_dare_author
import partygames.composeapp.generated.resources.truth_or_dare_description
import partygames.composeapp.generated.resources.truth_or_dare_how_to_play
import partygames.composeapp.generated.resources.truth_or_dare_title

class TruthOrDare : Game<TruthOrDareSettings, TruthOrDareDataset>() {
    override val gameId: String = "truth_or_dare"

    override var information: GameInformation = GameInformation(
        name = Res.string.truth_or_dare_title,
        description = Res.string.truth_or_dare_description,
        author = Res.string.truth_or_dare_author,
        colorLightTheme = Color.Green,
        colorDarkTheme = Color.Green,
        howToPlay = Res.string.truth_or_dare_how_to_play,
    )

    override var settings: TruthOrDareSettings? = null
    override val parseData: (JsonObject) -> TruthOrDareDataset? = { null }
    override val datasets: MutableList<TruthOrDareDataset> = mutableListOf()

    override val setupWidget = @Composable { modifier: Modifier ->
        Text("Setup ${stringResource(information.name)}", modifier = modifier)
    }

    override val playWidget = @Composable { modifier: Modifier ->
        Text("Play ${stringResource(information.name)}", modifier = modifier)
    }

    override val settingsWidget = null

    override fun createGame(
        settings: TruthOrDareSettings
    ) {
        this.settings = settings
    }
}

data class TruthOrDareSettings(val topics: List<String>, val ageMin: Int?, val ageMax: Int?)
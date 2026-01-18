package de.benkralex.partygames.games.justOne.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.app.activeGame
import de.benkralex.partygames.app.getKeyByGame
import de.benkralex.partygames.datasets.Dataset
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import de.benkralex.partygames.games.justOne.presentation.JustOnePlayWidget
import de.benkralex.partygames.games.justOne.presentation.JustOneSetupWidget
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.just_one_author
import partygames.composeapp.generated.resources.just_one_description
import partygames.composeapp.generated.resources.just_one_how_to_play
import partygames.composeapp.generated.resources.just_one_title

class JustOne : Game() {
    override val gameId: String = "just_one"

    override val information: GameInformation = GameInformation(
        name = Res.string.just_one_title,
        description = Res.string.just_one_description,
        author = Res.string.just_one_author,
        colorLightTheme = Color(0xffff9900),
        colorDarkTheme = Color(0xffff9900),
        howToPlay = Res.string.just_one_how_to_play,
    )

    override var settings: Map<String, Any?> = mapOf(
        "players" to null,
        "topics" to null,
        "hint" to null,
    )


    override val parseData: (JsonObject) -> Dataset? = ::parseData

    fun parseData(jsonObject: JsonObject): Dataset? {
        var dataset: Dataset? = null
        try {
            dataset = Json.decodeFromJsonElement<JustOneDataset>(jsonObject)
        } catch (e: Exception) {
            Napier.e("Error while decoding Just one dataset", e)
        }

        return dataset
    }

    override val datasets: MutableList<Dataset> = mutableListOf()

    override val setupWidget = @Composable { modifier: Modifier ->
        JustOneSetupWidget(
            modifier = modifier,
            setupGame = { players, topics ->
                createGame(
                    settings = mapOf(
                        "players" to players,
                        "topics" to activeDatasets.flatMap { (it as JustOneDataset).topics.toList() }.filter { topics.contains(it.second) },
                    ),
                )
            },
            topics = activeDatasets.flatMap { (it as JustOneDataset).topics.values },
        )
    }

    override val playWidget = @Composable { modifier: Modifier ->
        JustOnePlayWidget(
            modifier = modifier,
            game = this,
            datasets = activeDatasets.map { it as JustOneDataset },
        )
    }

    override val settingsWidget = null

    override fun createGame(
        settings: Map<String, Any?>
    ) {
        this.settings = settings
        activeGame = getKeyByGame(this)
    }
}
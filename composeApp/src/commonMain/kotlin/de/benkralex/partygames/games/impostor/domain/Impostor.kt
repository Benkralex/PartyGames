package de.benkralex.partygames.games.impostor.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.app.activeGame
import de.benkralex.partygames.app.getKeyByGame
import de.benkralex.partygames.games.common.domain.Dataset
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import de.benkralex.partygames.games.findLiar.domain.FindLiarDataset
import de.benkralex.partygames.games.impostor.data.parseImpostorDataset
import de.benkralex.partygames.games.impostor.presentation.ImpostorPlayWidget
import de.benkralex.partygames.games.impostor.presentation.ImpostorSetupWidget
import kotlinx.serialization.json.JsonObject
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.impostor_author
import partygames.composeapp.generated.resources.impostor_description
import partygames.composeapp.generated.resources.impostor_how_to_play
import partygames.composeapp.generated.resources.impostor_title

class Impostor : Game {
    override val gameId: String = "impostor"

    override val information: GameInformation = GameInformation(
        name = Res.string.impostor_title,
        description = Res.string.impostor_description,
        author = Res.string.impostor_author,
        colorLightTheme = Color.Blue,
        colorDarkTheme = Color.Blue,
        howToPlay = Res.string.impostor_how_to_play,
    )

    override var settings: Map<String, Any?> = mapOf(
        "players" to null,
        "impostorCount" to null,
        "topics" to null,
        "hint" to null,
    )
    override val parseData: (JsonObject) -> Dataset? = { parseImpostorDataset(it) }
    override val datasets: MutableList<Dataset> = mutableListOf()

    override val setupWidget = @Composable { modifier: Modifier ->
        ImpostorSetupWidget(
            modifier = modifier,
            setupGame = { players, impostorCount, topics ->
                createGame(
                    settings = mapOf(
                        "players" to players,
                        "impostorCount" to impostorCount,
                        "topics" to topics,
                    ),
                )
            },
            topics = datasets.flatMap { (it as ImpostorDataset).wordPairs }.filter { w ->
                val languages = de.benkralex.partygames.settingsPage.data.settings.value.languages
                w.mainWord.translations.keys.any { lang ->
                    lang in languages
                            || lang.split("_")[0] in languages
                            || lang in languages.map { it.split("_")[0] }
                } && w.impostorHintWord.translations.keys.any { lang ->
                    lang in languages
                            || lang.split("_")[0] in languages
                            || lang in languages.map { it.split("_")[0] }
                }
            }.map { it.topic }.toSet().toList(),
        )
    }

    override val playWidget = @Composable { modifier: Modifier ->
        ImpostorPlayWidget(
            modifier = modifier,
            game = this,
            datasets = datasets.map { it as ImpostorDataset },
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
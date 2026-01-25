package de.benkralex.partygames.games.impostor.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.app.activeGame
import de.benkralex.partygames.app.getKeyByGame
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.impostor.data.parseImpostorDataset
import de.benkralex.partygames.games.impostor.presentation.ImpostorPlayWidget
import de.benkralex.partygames.games.impostor.presentation.ImpostorSetupWidget
import kotlinx.serialization.json.JsonObject
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.impostor_author
import partygames.composeapp.generated.resources.impostor_description
import partygames.composeapp.generated.resources.impostor_how_to_play
import partygames.composeapp.generated.resources.impostor_title

class Impostor : Game<ImpostorSettings, ImpostorDataset>() {
    override val gameId: String = "impostor"

    override val information: GameInformation = GameInformation(
        name = Res.string.impostor_title,
        description = Res.string.impostor_description,
        author = Res.string.impostor_author,
        colorLightTheme = Color(0xFFC09CE3),
        colorDarkTheme = Color(0xFF3b0273),
        howToPlay = Res.string.impostor_how_to_play,
    )

    override var settings: ImpostorSettings? = null
    override val parseData: (JsonObject) -> ImpostorDataset? = { parseImpostorDataset(it) }
    override val datasets: MutableList<ImpostorDataset> = mutableListOf()

    override val setupWidget = @Composable { modifier: Modifier ->
        ImpostorSetupWidget(
            modifier = modifier,
            setupGame = { players, impostorCount, topics ->
                createGame(
                    settings = ImpostorSettings(
                        players = players,
                        impostorCount = impostorCount,
                        topics = topics,
                    ),
                )
            },
            topics = activeDatasets.flatMap { it.wordPairs }.filter { w ->
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
            datasets = activeDatasets,
        )
    }

    override val settingsWidget = null

    override fun createGame(
        settings: ImpostorSettings
    ) {
        this.settings = settings
        activeGame = getKeyByGame(this)
    }
}

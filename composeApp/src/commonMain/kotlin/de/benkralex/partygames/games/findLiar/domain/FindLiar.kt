package de.benkralex.partygames.games.findLiar.domain

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.benkralex.partygames.app.activeGame
import de.benkralex.partygames.app.getKeyByGame
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.domain.GameInformation
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.findLiar.data.parseFindLiarDataset
import de.benkralex.partygames.games.findLiar.presentation.FindLiarPlayWidget
import de.benkralex.partygames.games.findLiar.presentation.FindLiarSetupWidget
import kotlinx.serialization.json.JsonObject
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.find_liar_author
import partygames.composeapp.generated.resources.find_liar_description
import partygames.composeapp.generated.resources.find_liar_how_to_play
import partygames.composeapp.generated.resources.find_liar_title

class FindLiar : Game<FindLiarSettings, FindLiarDataset>() {
    override val gameId: String = "find_liar"

    override val information: GameInformation = GameInformation(
        name = Res.string.find_liar_title,
        description = Res.string.find_liar_description,
        author = Res.string.find_liar_author,
        colorLightTheme = Color(0xFF59c976),
        colorDarkTheme = Color(0xFF2d663c),
        howToPlay = Res.string.find_liar_how_to_play,
    )

    override var settings: FindLiarSettings? = null
    override val parseData: (JsonObject) -> FindLiarDataset? = { parseFindLiarDataset(it) }
    override val datasets: MutableList<FindLiarDataset> = mutableListOf()

    @OptIn(ExperimentalMaterial3Api::class)
    override val setupWidget = @Composable { modifier: Modifier ->
        FindLiarSetupWidget(
            modifier = modifier,
            setupGame = { players, liarCount, topics ->
                createGame(
                    FindLiarSettings(
                        players = players,
                        liarCount = liarCount,
                        topics = topics,
                    )
                )
            },
            topics = activeDatasets.flatMap { it.questionPairs }.filter { q ->
                val languages = de.benkralex.partygames.settingsPage.data.settings.value.languages
                q.liarQuestion.translations.keys.any { lang ->
                    lang in languages
                            || lang.split("_")[0] in languages
                            || lang in languages.map { it.split("_")[0] }
                } && q.mainQuestion.translations.keys.any { lang ->
                    lang in languages
                            || lang.split("_")[0] in languages
                            || lang in languages.map { it.split("_")[0] }
                }
            }.map { it.topic }.toSet().toList(),
        )
    }

    override val playWidget = @Composable { modifier: Modifier ->
        FindLiarPlayWidget(
            modifier = modifier,
            game = this,
            datasets = activeDatasets,
        )
    }

    override val settingsWidget = null

    override fun createGame(
        settings: FindLiarSettings,
    ) {
        this.settings = settings
        activeGame = getKeyByGame(this)
    }
}
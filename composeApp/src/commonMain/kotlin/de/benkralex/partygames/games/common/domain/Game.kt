package de.benkralex.partygames.games.common.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import de.benkralex.partygames.datasets.Dataset
import kotlinx.serialization.json.JsonObject

abstract class Game {
    //-------------
    // Information
    //-------------
    abstract val information: GameInformation
    abstract val gameId: String

    //-------------
    // Settings
    //-------------
    abstract val settings: Map<String, Any?>

    //-------------
    // Data
    //-------------
    abstract val parseData: (json: JsonObject) -> Dataset?
    abstract val datasets: MutableList<Dataset>
    val activeDatasets: List<Dataset>
        get() = datasets.filter { it.active }

    //-------------
    // UI
    //-------------
    abstract val setupWidget: @Composable (modifier: Modifier) -> Unit
    abstract val playWidget: @Composable (modifier: Modifier) -> Unit
    abstract val settingsWidget: (@Composable (modifier: Modifier) -> Unit)?

    //-------------
    // Game Logic
    //-------------
    abstract fun createGame(settings: Map<String, Any?>)
}
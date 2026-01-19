package de.benkralex.partygames.games.common.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.benkralex.partygames.datasets.Dataset
import kotlinx.serialization.json.JsonObject

abstract class Game<S, D : Dataset> {
    //-------------
    // Information
    //-------------
    abstract val information: GameInformation
    abstract val gameId: String

    //-------------
    // Settings
    //-------------
    abstract val settings: S?

    //-------------
    // Data
    //-------------
    abstract val parseData: (json: JsonObject) -> D?
    abstract val datasets: MutableList<D>
    val activeDatasets: List<D>
        get() = datasets.filter { it.active }

    //-------------
    // UI
    //-------------
    abstract val setupWidget: @Composable (modifier: Modifier) -> Unit
    abstract val playWidget: @Composable (modifier: Modifier) -> Unit
    abstract val settingsWidget: (@Composable (modifier: Modifier) -> Unit)?

    //-------------
    // Game Logic
    /**
 * Initializes a new game instance using the provided settings.
 *
 * @param settings Configuration values used to create and configure the game instance.
 */
    abstract fun createGame(settings: S)
}
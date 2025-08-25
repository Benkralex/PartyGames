package de.benkralex.partygames.games.common.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.json.JsonObject

interface Game {
    //-------------
    // Information
    //-------------
    val information: GameInformation
    val gameId: String

    //-------------
    // Settings
    //-------------
    val settings: Map<String, Any?>

    //-------------
    // Data
    //-------------
    val parseData: (json: JsonObject) -> Dataset?
    val datasets: MutableList<Dataset>

    //-------------
    // UI
    //-------------
    val setupWidget: @Composable (modifier: Modifier) -> Unit
    val playWidget: @Composable (modifier: Modifier) -> Unit
    val settingsWidget: (@Composable (modifier: Modifier) -> Unit)?

    //-------------
    // Game Logic
    //-------------
    fun createGame(settings: Map<String, Any?>)
}
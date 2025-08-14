package de.benkralex.partygames.games.common.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Game {
    //-------------
    // Information
    //-------------
    val information: GameInformation

    //-------------
    // Settings
    //-------------
    val settings: Map<String, Any?>

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
package de.benkralex.partygames.app

import de.benkralex.partygames.games.common.domain.Game
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object GameSelection : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data class GameSettings(val gameKey: String) : Route

    @Serializable
    data object GamePlay : Route
}
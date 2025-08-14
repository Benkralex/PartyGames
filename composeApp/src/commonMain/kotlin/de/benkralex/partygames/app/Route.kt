package de.benkralex.partygames.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object GameSelectionRoute : Route

    @Serializable
    data object SettingsRoute : Route

    @Serializable
    data class GameSetupRoute(val gameKey: String) : Route

    @Serializable
    data object GamePlayRoute : Route
}
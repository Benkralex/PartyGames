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

    @Serializable
    data object LANPartyOverviewRoute : Route

    @Serializable
    data object LANPartyHostRoute : Route

    @Serializable
    data class LANPartyManageRoute(val port: Int, val hostPlays: Boolean, val name: String) : Route

    @Serializable
    data object LANPartyJoinRoute : Route
}
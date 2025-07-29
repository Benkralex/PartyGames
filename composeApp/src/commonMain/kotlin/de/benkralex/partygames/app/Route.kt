package de.benkralex.partygames.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object OutOfGame: Route

    @Serializable
    data object GameSelection : Route

    @Serializable
    data object GameSettings : Route
}
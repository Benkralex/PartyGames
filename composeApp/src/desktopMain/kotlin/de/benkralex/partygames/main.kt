package de.benkralex.partygames

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.benkralex.partygames.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PartyGames",
    ) {
        App()
    }
}
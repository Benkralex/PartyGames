package de.benkralex.partygames

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.benkralex.partygames.app.App
import de.benkralex.partygames.app.DATA_STORE_FILE_NAME
import de.benkralex.partygames.app.createDataStore

fun main() {
    val prefs = createDataStore {
        DATA_STORE_FILE_NAME
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "PartyGames",
        ) {
            App(
                prefs = prefs
            )
        }
    }
}
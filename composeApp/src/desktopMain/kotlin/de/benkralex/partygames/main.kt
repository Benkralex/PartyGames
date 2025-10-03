package de.benkralex.partygames

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import de.benkralex.partygames.app.App
import de.benkralex.partygames.app.DATA_STORE_FILE_NAME
import okio.Path.Companion.toPath

lateinit var prefs: DataStore<Preferences>

fun main() {
    prefs = PreferenceDataStoreFactory.createWithPath(
        produceFile = { (System.getProperty("user.home") + "/.partygames/.pref/$DATA_STORE_FILE_NAME").toPath() }
    )
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "PartyGames",
        ) {
            App()
        }
    }
}
package de.benkralex.partygames.settingsPage.data

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import de.benkralex.partygames.prefs
import io.github.aakira.napier.Napier

private val languagesKey = stringPreferencesKey("languages")
private val lastPlayersKey = stringPreferencesKey("lastPlayers")
private val datasetPathKey = stringPreferencesKey("datasetPath")

actual suspend fun saveSettings() {
    prefs.edit { preferences ->
        preferences[languagesKey] = settings.value.languages.toPreferencesString()
        preferences[lastPlayersKey] = settings.value.lastPlayers.toPreferencesString()
        preferences[datasetPathKey] = settings.value.datasetPath
    }
    Napier.d( message =
        "Settings Saved: \n" +
                "Languages: ${settings.value.languages} \n" +
                "LastPlayers: ${settings.value.lastPlayers} \n" +
                "DatasetPath: ${settings.value.datasetPath} \n"
    )
}

actual suspend fun loadSettings() {
    prefs.edit { preferences ->
        settings.value = Settings(
            languages = preferences[languagesKey]?.toListOfPrefString() ?: mutableListOf(),
            lastPlayers = preferences[lastPlayersKey]?.toListOfPrefString() ?: mutableListOf(),
            datasetPath = preferences[datasetPathKey] ?: "",
        )
    }
}

private fun List<String>.toPreferencesString(): String {
    return this
        .joinToString("/,") { str ->
            str.replace(",", "\\,")
        }
}

private fun String.toListOfPrefString(): List<String> {
    return this.split("/,").map { str ->
        str.replace("\\,", ",")
    }.filter { it.isNotBlank() }
}
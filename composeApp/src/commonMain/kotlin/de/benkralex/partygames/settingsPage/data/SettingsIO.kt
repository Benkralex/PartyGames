package de.benkralex.partygames.settingsPage.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

var settings: MutableState<Settings> = mutableStateOf(Settings())
private val languagesKey = stringPreferencesKey("languages")
private val lastPlayersKey = stringPreferencesKey("lastPlayers")


suspend fun saveSettings(prefs: DataStore<Preferences>) {
    prefs.edit { preferences ->
        preferences[languagesKey] = settings.value.languages.toPreferencesString()
        preferences[lastPlayersKey] = settings.value.lastPlayers.toPreferencesString()
    }
}

suspend fun loadSettings(prefs: DataStore<Preferences>) {
    prefs.edit { preferences ->
        settings.value = Settings(
            languages = preferences[languagesKey]?.toList() ?: emptyList(),
            lastPlayers = preferences[lastPlayersKey]?.toList() ?: emptyList()
        )
    }
}

private fun List<String>.toPreferencesString(): String {
    return this
        .joinToString("/,") { str ->
            str.replace(",", "\\,")
        }
}

private fun String.toList(): List<String> {
    return this.split("/,").map { str ->
        str.replace("\\,", ",")
    }.filter { it.isNotBlank() }
}

data class Settings (
    var languages: List<String> = mutableListOf(),
    var lastPlayers: List<String> = mutableListOf(),
)
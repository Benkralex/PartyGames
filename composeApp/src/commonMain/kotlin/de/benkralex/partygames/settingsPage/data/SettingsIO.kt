package de.benkralex.partygames.settingsPage.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.aakira.napier.Napier

var settings: MutableState<Settings> = mutableStateOf(Settings())
private val languagesKey = stringPreferencesKey("languages")
private val lastPlayersKey = stringPreferencesKey("lastPlayers")
private val datasetPathKey = stringPreferencesKey("datasetPath")
private val gitRepoDatasetUrlsKey = stringPreferencesKey("gitRepoDatasetUrls")


suspend fun saveSettings(prefs: DataStore<Preferences>) {
    prefs.edit { preferences ->
        preferences[languagesKey] = settings.value.languages.toPreferencesString()
        preferences[lastPlayersKey] = settings.value.lastPlayers.toPreferencesString()
        preferences[datasetPathKey] = settings.value.datasetPath
        preferences[gitRepoDatasetUrlsKey] = settings.value.gitRepoDatasetUrls.toPreferencesString()
    }
    Napier.d( message =
        "Settings Saved: \n" +
        "Languages: ${settings.value.languages} \n" +
        "LastPlayers: ${settings.value.lastPlayers} \n" +
        "DatasetPath: ${settings.value.datasetPath} \n" +
        "GitRepoDatasetUrls: ${settings.value.gitRepoDatasetUrls} \n"
    )
}

suspend fun loadSettings(prefs: DataStore<Preferences>) {
    prefs.edit { preferences ->
        settings.value = Settings(
            languages = preferences[languagesKey]?.toList() ?: mutableListOf(),
            lastPlayers = preferences[lastPlayersKey]?.toList() ?: mutableListOf(),
            datasetPath = preferences[datasetPathKey] ?: "",
            gitRepoDatasetUrls = preferences[gitRepoDatasetUrlsKey]?.toList() ?: mutableListOf(),
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
    var datasetPath: String = "",
    var gitRepoDatasetUrls: List<String> = mutableListOf(),
)
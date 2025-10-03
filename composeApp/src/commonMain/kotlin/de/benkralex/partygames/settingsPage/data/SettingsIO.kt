package de.benkralex.partygames.settingsPage.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

var settings: MutableState<Settings> = mutableStateOf(Settings())

expect suspend fun saveSettings()

expect suspend fun loadSettings()

data class Settings (
    var languages: List<String> = mutableListOf(),
    var lastPlayers: List<String> = mutableListOf(),
    var datasetPath: String = "",
)
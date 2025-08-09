package de.benkralex.partygames

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import createDataStore
import de.benkralex.partygames.app.App

fun MainViewController() = ComposeUIViewController {
    App(
        prefs = remember {
            createDataStore()
        }
    )
}
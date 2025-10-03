package de.benkralex.partygames.settingsPage.presentation.settingsWidgets

import androidx.compose.runtime.Composable

@Composable
actual fun OpenFilePicker(onPathSelected: (String) -> Unit) {
    onPathSelected("")
}
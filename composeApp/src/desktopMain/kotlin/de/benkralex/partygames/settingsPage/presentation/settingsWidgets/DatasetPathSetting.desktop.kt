package de.benkralex.partygames.settingsPage.presentation.settingsWidgets

import androidx.compose.runtime.Composable
import io.github.aakira.napier.Napier
import javax.swing.JFileChooser

@Composable
actual fun OpenFilePicker(onPathSelected: (String) -> Unit) {
    val fileChooser = JFileChooser()
    fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    val returnValue = fileChooser.showOpenDialog(null)
    if (returnValue == JFileChooser.APPROVE_OPTION) {
        val selectedFile = fileChooser.selectedFile
        onPathSelected(selectedFile.absolutePath)
        Napier.d("Selected path: ${selectedFile.absolutePath}")
    }
}
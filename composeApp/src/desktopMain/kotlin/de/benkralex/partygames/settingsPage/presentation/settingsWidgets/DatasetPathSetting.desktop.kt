package de.benkralex.partygames.settingsPage.presentation.settingsWidgets

import javax.swing.JFileChooser

actual fun openFilePicker(onPathSelected: (String) -> Unit) {
    val fileChooser = JFileChooser()
    fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    val returnValue = fileChooser.showOpenDialog(null)
    if (returnValue == JFileChooser.APPROVE_OPTION) {
        val selectedFile = fileChooser.selectedFile
        onPathSelected(selectedFile.absolutePath)
    }
}
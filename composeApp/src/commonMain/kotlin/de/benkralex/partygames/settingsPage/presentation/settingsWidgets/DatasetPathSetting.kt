package de.benkralex.partygames.settingsPage.presentation.settingsWidgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import de.benkralex.partygames.settingsPage.data.settings

@Composable
fun DatasetPathSetting(
    modifier: Modifier = Modifier,
    viewModel: DatasetPathSettingViewModel,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = viewModel.datasetPath,
        onValueChange = { viewModel.onPathSelected(it) },
        label = { Text("Dataset Path") },
        placeholder = { Text("Enter dataset path") },
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = { viewModel.openFilePicker() },
            ) {
                Icon(
                    imageVector = Icons.Default.FolderOpen,
                    contentDescription = "Open File Picker"
                )
            }
        }
    )
}

class DatasetPathSettingViewModel() {
    val datasetPath: String by derivedStateOf {
        settings.value.datasetPath
    }
    fun onPathSelected(path: String) {
        settings.value.datasetPath = path
    }

    fun openFilePicker() {
        openFilePicker {
            onPathSelected(it)
        }
    }
}

expect fun openFilePicker(onPathSelected: (String) -> Unit)
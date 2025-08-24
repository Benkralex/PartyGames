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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import de.benkralex.partygames.settingsPage.data.settings
import kotlin.reflect.KClass
import kotlin.reflect.cast

@Composable
fun DatasetPathSetting(
    modifier: Modifier = Modifier,
    viewModel: DatasetPathSettingViewModel = viewModel<DatasetPathSettingViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return modelClass.cast(DatasetPathSettingViewModel())
            }
        }
    ),
) {
    var openFilePicker: Boolean by remember {
        mutableStateOf(false)
    }

    if (openFilePicker) {
        viewModel.OpenFilePicker()
        openFilePicker = false
    }


    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = viewModel.datasetPath,
        onValueChange = { viewModel.onPathSelected(it) },
        label = { Text("Dataset Path") },
        placeholder = { Text("Enter dataset path") },
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = {
                    openFilePicker = true
                },
            ) {
                Icon(
                    imageVector = Icons.Default.FolderOpen,
                    contentDescription = "Open File Picker"
                )
            }
        }
    )
}

class DatasetPathSettingViewModel() : ViewModel() {
    val datasetPath: String by derivedStateOf {
        settings.value.datasetPath
    }
    fun onPathSelected(path: String) {
        settings.value = settings.value.copy(datasetPath = path)
    }

    @Composable
    fun OpenFilePicker() {
        OpenFilePicker {
            onPathSelected(it)
        }
    }
}

@Composable
expect fun OpenFilePicker(onPathSelected: (String) -> Unit)
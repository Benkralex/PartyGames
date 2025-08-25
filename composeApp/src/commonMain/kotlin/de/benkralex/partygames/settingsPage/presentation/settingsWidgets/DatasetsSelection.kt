package de.benkralex.partygames.settingsPage.presentation.settingsWidgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.benkralex.partygames.games.common.data.getJsonFiles
import de.benkralex.partygames.settingsPage.data.settings
import io.github.aakira.napier.Napier

@Composable
fun DatasetSelection() {
    Column {
        var datasets by remember {
            mutableStateOf(listOf<String>())
        }
        LaunchedEffect(Unit) {
            Napier.i("Loading datasets from path: ${settings.value.datasetPath}")
            datasets = getJsonFiles(settings.value.datasetPath).toMutableList()
        }
        if (datasets.isEmpty()) {
            CircularProgressIndicator()
        } else {
            datasets.forEach { dataset ->
                Text("Dataset: $dataset")
            }
        }
    }
}
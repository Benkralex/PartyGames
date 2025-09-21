package de.benkralex.partygames.settingsPage.presentation.settingsWidgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.app.gamesRegister
import de.benkralex.partygames.app.local

@Composable
fun DatasetSelection(
    modifier: Modifier = Modifier,
) {
    val datasets by remember { derivedStateOf {
        gamesRegister.map { it.datasets }.flatten()
    }}
    LazyColumn (
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(datasets) {
            var active by remember { mutableStateOf(it.active) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = if (active) {
                            2.dp
                        } else {
                            1.dp
                        },
                        color = if (active) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        active = !active
                        it.active = active
                    },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    Text(
                        text = it.title[local],
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    Text(
                        text = it.author[local],
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        text = it.description[local],
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}
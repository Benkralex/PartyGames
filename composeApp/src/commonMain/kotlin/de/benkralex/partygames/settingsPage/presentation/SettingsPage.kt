package de.benkralex.partygames.settingsPage.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.benkralex.partygames.settingsPage.data.settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    onNavigateBack: () -> Unit = {},
) {
    Scaffold (
        topBar = {
            SettingsTopAppBar(
                onNavigateBack = onNavigateBack,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Languages: ${settings.value.languages} \n LastPlayers: ${settings.value.lastPlayers}",
            )
        }
    }
}
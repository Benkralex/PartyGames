package de.benkralex.partygames.settingsPage.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.settingsPage.data.Settings
import de.benkralex.partygames.settingsPage.data.settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
    onNavigateBack: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = "Settings") },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.padding(8.dp),
                )
            }
        },
        actions = {
            Row {
                IconButton(
                    onClick = {
                        settings.value = Settings()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Replay,
                        contentDescription = "Reset Settings",
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }
    )
}
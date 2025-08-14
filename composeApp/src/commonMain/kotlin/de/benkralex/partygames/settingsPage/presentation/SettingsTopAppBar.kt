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
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.content_description_back_button
import partygames.composeapp.generated.resources.content_description_reset_settings_button
import partygames.composeapp.generated.resources.settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
    onNavigateBack: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.settings),
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.content_description_back_button),
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
                        contentDescription = stringResource(Res.string.content_description_reset_settings_button),
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }
    )
}
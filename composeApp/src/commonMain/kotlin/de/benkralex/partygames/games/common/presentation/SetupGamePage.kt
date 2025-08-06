package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Game
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.accept_dialog
import partygames.composeapp.generated.resources.how_to_play

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupGamePage(
    game: Game,
    onNavigateBack: () -> Unit = {},
) {
    var showInfoDialog by remember { mutableStateOf(false) }
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = {
                Text(
                    text = stringResource(Res.string.how_to_play),
                )
            },
            text = {
                Text(
                    text = stringResource(game.information.howToPlay),
                )
           },
            confirmButton = {
                TextButton(
                    onClick = { showInfoDialog = false }
                ) {
                    Text(
                        text = stringResource(Res.string.accept_dialog),
                    )
                }
            }
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(game.information.name),
                    )
                },
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
                    IconButton(
                        onClick = {
                            showInfoDialog = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Info",
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        game.setupWidget(
            Modifier
                .padding(paddingValues)
        )
    }
}
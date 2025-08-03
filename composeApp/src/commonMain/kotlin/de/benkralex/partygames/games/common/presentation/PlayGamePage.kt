package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.games.common.domain.Game
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayGamePage(
    game: Game,
    onNavigateBack: () -> Unit = {},
) {
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
                }
            )
        }
    ) { paddingValues ->
        game.playWidget(
            Modifier
                .padding(paddingValues)
        )
    }
}
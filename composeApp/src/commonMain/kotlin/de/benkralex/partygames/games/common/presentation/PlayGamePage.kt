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
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.content_description_back_button

/**
 * Displays the play screen for the given game with a top app bar showing the game's name and a back button.
 *
 * The composable places the game's play UI into a Scaffold so the content is inset by the scaffold's padding.
 *
 * @param game The game instance whose play UI and display name are presented.
 * @param onNavigateBack Callback invoked when the top app bar back button is pressed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayGamePage(
    game: Game<*,*>,
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
                            contentDescription = stringResource(Res.string.content_description_back_button),
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
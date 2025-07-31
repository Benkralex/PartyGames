package de.benkralex.partygames.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.benkralex.partygames.app.theme.AppTheme
import de.benkralex.partygames.gameSelectionPage.presentation.GameSelectionPage
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.presentation.SetupGamePage
import de.benkralex.partygames.settingsPage.presentation.SettingsPage
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.GameSelection,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                ExitTransition.None
            }
        ) {
            composable<Route.GameSelection> {
                GameSelectionPage(
                    onNavigateToSettings = {
                        navController.navigate(Route.Settings)
                    },
                    onNavigateToGame = { game ->
                        navController.navigate(
                            Route.GameSettings(gameKey = getKeyByGame(game))
                        )
                    }
                )
            }
            composable<Route.Settings> {
                SettingsPage(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                )
            }
            composable<Route.GameSettings> {
                if (it.arguments == null) navController.navigateUp()
                val game: Game? = getGameByKey(it.arguments!!.getString("gameKey", ""))
                if (game == null) navController.navigateUp()

                SetupGamePage(
                    game = game!!,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}
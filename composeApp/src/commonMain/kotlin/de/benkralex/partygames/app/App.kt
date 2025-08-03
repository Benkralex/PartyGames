package de.benkralex.partygames.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.benkralex.partygames.app.theme.AppTheme
import de.benkralex.partygames.gameSelectionPage.presentation.GameSelectionPage
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.presentation.PlayGamePage
import de.benkralex.partygames.games.common.presentation.SetupGamePage
import de.benkralex.partygames.settingsPage.presentation.SettingsPage
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    Napier.base(DebugAntilog())
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
                activeGame = null
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
                activeGame = null
                SettingsPage(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                )
            }
            composable<Route.GameSettings> {
                if (it.arguments == null) {
                    Napier.e("No arguments found for GameSettings")
                    navController.navigateUp()
                }
                val game: Game? = getGameByKey(it.arguments!!.getString("gameKey", ""))
                if (game == null) {
                    Napier.e("Game with key ${it.arguments!!.getString("gameKey", "")} not found")
                    navController.navigateUp()
                }

                LaunchedEffect(activeGame) {
                    if (activeGame != null) {
                        navController.navigate(Route.GamePlay)
                    }
                }

                SetupGamePage(
                    game = game!!,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable<Route.GamePlay> {
                if (activeGame == null) {
                    Napier.e("No active game found")
                    navController.navigateUp()
                    return@composable
                }
                val game = getGameByKey(activeGame!!)
                if (game == null) {
                    Napier.e("Game with key $activeGame not found")
                    navController.navigateUp()
                    return@composable
                }

                LaunchedEffect(activeGame) {
                    if (activeGame == null) {
                        navController.navigate(Route.GameSelection)
                    }
                }

                PlayGamePage(
                    game = game!!,
                    onNavigateBack = {
                        navController.navigate(Route.GameSelection)
                    }
                )
            }
        }
    }
}
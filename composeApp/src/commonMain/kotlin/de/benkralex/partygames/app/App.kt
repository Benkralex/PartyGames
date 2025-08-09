package de.benkralex.partygames.app

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.benkralex.partygames.app.theme.AppTheme
import de.benkralex.partygames.gameSelectionPage.presentation.GameSelectionPage
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.presentation.PlayGamePage
import de.benkralex.partygames.games.common.presentation.SetupGamePage
import de.benkralex.partygames.games.findLiar.data.updateQuestionDatasets
import de.benkralex.partygames.settingsPage.data.loadSettings
import de.benkralex.partygames.settingsPage.data.saveSettings
import de.benkralex.partygames.settingsPage.data.settings
import de.benkralex.partygames.settingsPage.presentation.SettingsPage
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App(
    prefs: DataStore<Preferences>
) {
    Napier.base(DebugAntilog())
    AppTheme {
        LaunchedEffect(Unit) {
            updateQuestionDatasets()
            loadSettings(prefs)
        }

        LaunchedEffect(settings.value) {
            Napier.i("Settings Saved: \nLanguages: ${settings.value.languages} \nLastPlayers: ${settings.value.lastPlayers}")
            saveSettings(prefs)
        }

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
            composable<Route.GameSettings> { backStackEntry ->
                val gameSettings = backStackEntry.toRoute<Route.GameSettings>()
                val game: Game? = getGameByKey(gameSettings.gameKey)
                if (game == null) {
                    Napier.e("Game with key ${gameSettings.gameKey} not found")
                    navController.navigateUp()
                    return@composable
                }

                LaunchedEffect(activeGame) {
                    Napier.i("Active game changed")
                    if (activeGame != null) {
                        Napier.i("Active game already set, navigating to GamePlay")
                        navController.navigate(Route.GamePlay) {
                            popUpTo<Route.GameSettings> { inclusive = true }
                        }
                    }
                }

                SetupGamePage(
                    game = game,
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

                BackHandler {
                    navController.navigate(Route.GameSelection)
                }

                PlayGamePage(
                    game = game,
                    onNavigateBack = {
                        navController.navigate(Route.GameSelection)
                    }
                )
            }
        }
    }
}
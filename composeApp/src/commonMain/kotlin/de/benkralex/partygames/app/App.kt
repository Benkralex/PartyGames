package de.benkralex.partygames.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.intl.Locale
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
import de.benkralex.partygames.games.findLiar.data.updateFindLiarDatasets
import de.benkralex.partygames.games.impostor.data.updateImpostorDatasets
import de.benkralex.partygames.settingsPage.data.loadSettings
import de.benkralex.partygames.settingsPage.data.saveSettings
import de.benkralex.partygames.settingsPage.data.settings
import de.benkralex.partygames.settingsPage.presentation.SettingsPage
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.cancel_dialog
import partygames.composeapp.generated.resources.exit_game
import partygames.composeapp.generated.resources.exit_game_desc

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App(
    prefs: DataStore<Preferences>,
    theme: ColorScheme? = null,
) {
    Napier.base(DebugAntilog())
    AppTheme (
        theme = theme
    ) {
        LaunchedEffect(Unit) {
            // Async setup functions
            updateFindLiarDatasets()
            updateImpostorDatasets()
            loadSettings(prefs)
            if (settings.value.languages.isEmpty()) {
                settings.value.languages =
                    listOf(Locale.current.language + "_" + Locale.current.region)
            }
        }

        LaunchedEffect(settings.value) {
            saveSettings(prefs)
        }

        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.GameSelectionRoute,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            composable<Route.GameSelectionRoute> {
                activeGame = null
                GameSelectionPage(
                    onNavigateToSettings = {
                        navController.navigate(Route.SettingsRoute)
                    },
                    onNavigateToGame = { game ->
                        navController.navigate(
                            Route.GameSetupRoute(gameKey = getKeyByGame(game))
                        )
                    }
                )
            }


            composable<Route.SettingsRoute> {
                activeGame = null
                SettingsPage(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                )
            }


            composable<Route.GameSetupRoute> { backStackEntry ->
                val gameSettings = backStackEntry.toRoute<Route.GameSetupRoute>()
                val game: Game? = getGameByKey(gameSettings.gameKey)
                if (game == null) {
                    Napier.e("Game with key ${gameSettings.gameKey} not found, navigating back")
                    navController.navigateUp()
                    return@composable
                }

                LaunchedEffect(activeGame) {
                    if (activeGame != null) {
                        navController.navigate(Route.GamePlayRoute) {
                            popUpTo<Route.GameSetupRoute> { inclusive = true }
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


            composable<Route.GamePlayRoute> {
                if (activeGame == null) {
                    Napier.e("No active game found, navigating back")
                    navController.navigateUp()
                    return@composable
                }
                val game = getGameByKey(activeGame!!)
                if (game == null) {
                    Napier.e("Game with key $activeGame not found, navigating back")
                    activeGame = null
                    navController.navigateUp()
                    return@composable
                }

                var showExitDialog by remember { mutableStateOf(false) }

                BackHandler(true) {
                    showExitDialog = true
                }

                PlayGamePage(
                    game = game,
                    onNavigateBack = {
                        showExitDialog = true
                    },
                )

                if (showExitDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showExitDialog = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showExitDialog = false
                                    navController.navigate(Route.GameSelectionRoute) {
                                        popUpTo(Route.GameSelectionRoute) { inclusive = true }
                                    }
                                }
                            ) {
                                Text(stringResource(Res.string.exit_game))
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showExitDialog = false
                                }
                            ) {
                                Text(stringResource(Res.string.cancel_dialog))
                            }
                        },
                        title = {
                            Text(stringResource(Res.string.exit_game) + "?")
                        },
                        text = {
                            Text(stringResource(Res.string.exit_game_desc))
                        },
                    )
                }
            }
        }
    }
}
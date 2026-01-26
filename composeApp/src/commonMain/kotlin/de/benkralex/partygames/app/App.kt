package de.benkralex.partygames.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.benkralex.partygames.app.theme.AppTheme
import de.benkralex.partygames.datasets.loadDatasets
import de.benkralex.partygames.gameSelectionPage.presentation.GameSelectionPage
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.common.presentation.PlayGamePage
import de.benkralex.partygames.games.common.presentation.SetupGamePage
import de.benkralex.partygames.lanParty.data.getIPAddress
import de.benkralex.partygames.lanParty.domain.Host
import de.benkralex.partygames.lanParty.domain.NetworkManager
import de.benkralex.partygames.lanParty.domain.ServerManager
import de.benkralex.partygames.lanParty.presentation.LanPartyHost
import de.benkralex.partygames.lanParty.presentation.LanPartyJoin
import de.benkralex.partygames.lanParty.presentation.LanPartyManage
import de.benkralex.partygames.lanParty.presentation.LanPartyOverview
import de.benkralex.partygames.lanParty.presentation.QRCode
import de.benkralex.partygames.settingsPage.data.loadSettings
import de.benkralex.partygames.settingsPage.data.saveSettings
import de.benkralex.partygames.settingsPage.data.settings
import de.benkralex.partygames.settingsPage.presentation.SettingsPage
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.cancel_dialog
import partygames.composeapp.generated.resources.exit_game
import partygames.composeapp.generated.resources.exit_game_desc

lateinit var local: String
internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"
expect val PLATFORM: String
val networkManager = NetworkManager()

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App(
    theme: ColorScheme? = null,
) {
    Napier.base(DebugAntilog())
    AppTheme (
        theme = theme
    ) {
        local = Locale.current.language + "_" + Locale.current.region

        LaunchedEffect(Unit) {
            loadSettings()
            if (settings.value.languages.isEmpty()) {
                settings.value.languages =
                    listOf(local)
            }
        }

        LaunchedEffect(settings.value, Unit) {
            saveSettings()
            //Thread {
                //runBlocking {
                    launch {
                        loadDatasets(settings.value.datasetPath)
                    }
                //}
            //}.start()
        }

        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.GameSelectionRoute,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
        ) {
            composable<Route.GameSelectionRoute> {
                activeGame = null
                GameSelectionPage(
                    onNavigateToSettings = {
                        navController.navigate(Route.SettingsRoute)
                    },
                    onNavigateToLANParty = {
                        navController.navigate(Route.LANPartyOverviewRoute)
                    },
                    onNavigateToGame = { game ->
                        navController.navigate(
                            Route.GameSetupRoute(gameKey = getKeyByGame(game))
                        )
                    },
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
                val game: Game<*,*>? = getGameByKey(gameSettings.gameKey)
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


            composable<Route.LANPartyOverviewRoute> {
                activeGame = null
                LanPartyOverview(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onNavigateToHostGame = {
                        navController.navigate(Route.LANPartyHostRoute)
                    },
                    onNavigateToJoinGame = {
                        navController.navigate(Route.LANPartyJoinRoute)
                    },
                )
            }


            composable<Route.LANPartyHostRoute> {
                if (PLATFORM == "WEB") navController.navigateUp()
                activeGame = null
                var port: Int by remember { mutableStateOf(3333) }
                var hostPlays: Boolean by remember { mutableStateOf(false) }
                var name: String by remember { mutableStateOf("") }
                var navigate: Boolean by remember { mutableStateOf(false) }
                LaunchedEffect(navigate) {
                    if (navigate) {
                        navController.navigate(
                            Route.LANPartyManageRoute(
                                port = port,
                                hostPlays = hostPlays,
                                name = name,
                            )
                        )
                    }
                }
                LanPartyHost(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onGameStarted = { p, hP, n ->
                        Napier.d("port: $p; hostPlays: $hP; name: $n")
                        port = p
                        hostPlays = hP
                        name = n
                        navigate = true
                    }
                )
            }


            composable<Route.LANPartyManageRoute> { backStackEntry ->
                if (PLATFORM == "WEB") navController.navigateUp()
                val lanPartySettings = backStackEntry.toRoute<Route.LANPartyManageRoute>()

                LaunchedEffect(lanPartySettings) {
                    if (lanPartySettings.hostPlays) {
                        // Small delay to ensure server is fully started before connecting
                        kotlinx.coroutines.delay(500)
                        networkManager.host = Host(
                            hostName = "127.0.0.1",
                            port = lanPartySettings.port,
                            path = "/game",
                            protocol = "ws"
                        )
                        networkManager.createConnection()
                        networkManager.join(lanPartySettings.name)
                    }
                }

                LanPartyManage(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    port = lanPartySettings.port,
                    hostPlays = lanPartySettings.hostPlays,
                    hostName = lanPartySettings.name,
                )
            }


            composable<Route.LANPartyJoinRoute> {
                activeGame = null
                LanPartyJoin(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}
package de.benkralex.partygames.app

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import de.benkralex.partygames.gameSelection.presentation.GameSelectionPage
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.OutOfGame
        ) {
            navigation<Route.OutOfGame>(
                startDestination = Route.GameSelection,
            ) {
                composable<Route.GameSelection> {
                    GameSelectionPage()
                }
                composable<Route.GameSettings> {
                    // Placeholder for GameSettingsPage
                    // GameSettingsPage(onNavigateTo = navController::navigate)
                }
            }
        }
    }
}
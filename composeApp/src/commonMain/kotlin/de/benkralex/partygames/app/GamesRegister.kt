package de.benkralex.partygames.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.findLiar.domain.FindLiar
import de.benkralex.partygames.games.impostor.domain.Impostor
//import de.benkralex.partygames.games.truthOrDare.domain.TruthOrDare

val gamesRegister: List<Game<*,*>> = listOf(
    Impostor(),
    FindLiar(),
    //TruthOrDare(),
)

var activeGame: String? by mutableStateOf(null)

/**
 * Finds the registered game whose name key matches the provided key.
 *
 * @param key The unique name key of the game to look up.
 * @return The matching `Game<*,*>` if found, `null` otherwise.
 */
fun getGameByKey(key: String): Game<*,*>? {
    return gamesRegister.firstOrNull { it.information.name.key == key }
}

/**
 * Retrieves the registration key for the given game.
 *
 * @param game The game whose key to retrieve.
 * @return The game's name key.
 */
fun getKeyByGame(game: Game<*,*>): String {
    return game.information.name.key
}

/**
 * Finds a registered game by its gameId.
 *
 * @param gameId The unique identifier of the game to find.
 * @return The matching Game<*,*> if one exists, `null` otherwise.
 */
fun getGameByGameId(gameId: String): Game<*,*>? {
    return gamesRegister.firstOrNull { it.gameId == gameId }
}
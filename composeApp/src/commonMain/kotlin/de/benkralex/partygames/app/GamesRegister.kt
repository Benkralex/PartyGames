package de.benkralex.partygames.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.findLiar.domain.FindLiar
import de.benkralex.partygames.games.impostor.domain.Impostor
import de.benkralex.partygames.games.truthOrDare.domain.TruthOrDare

val gamesRegister: List<Game> = listOf(
    Impostor(),
    //TruthOrDare(),
    FindLiar(),
)

var activeGame: String? by mutableStateOf(
    null
)

fun getGameByKey(key: String): Game? {
    return gamesRegister.firstOrNull { it.information.name.key == key }
}

fun getKeyByGame(game: Game): String {
    return game.information.name.key
}
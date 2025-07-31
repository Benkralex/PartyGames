package de.benkralex.partygames.app

import de.benkralex.partygames.games.common.domain.Game
import de.benkralex.partygames.games.findLiar.data.FindLiar
import de.benkralex.partygames.games.impostor.data.Impostor
import de.benkralex.partygames.games.truthOrDare.data.TruthOrDare

val gamesRegister: List<Game> = listOf(
    Impostor(),
    TruthOrDare(),
    FindLiar(),
)

fun getGameByKey(key: String): Game? {
    return gamesRegister.firstOrNull { it.information.name.key == key }
}

fun getKeyByGame(game: Game): String {
    return game.information.name.key
}
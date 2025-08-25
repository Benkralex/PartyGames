package de.benkralex.partygames.games.common.data

import de.benkralex.partygames.app.gamesRegister
import de.benkralex.partygames.app.getGameByGameId
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive


expect suspend fun getJsonFiles(basePath: String): List<String>

expect suspend fun getJsonFileContent(path: String): ByteArray

suspend fun loadDatasets(basePath: String) {
    for (game in gamesRegister) {
        game.datasets.clear()
    }
    val paths = getJsonFiles(basePath)
    for (path in paths) {
        val bytes = getJsonFileContent(path)
        if (bytes.isNotEmpty()) {
            try {
                val jsonString = bytes.decodeToString()
                val json: JsonObject = Json.parseToJsonElement(jsonString) as JsonObject
                val gameId = json["game"]?.jsonPrimitive?.content ?: continue
                val game = getGameByGameId(gameId) ?: continue
                game.parseData(json)?.let { dataset ->
                    Napier.i("Loaded dataset for game $gameId")
                    game.datasets.add(dataset)
                } ?: Napier.e("Failed to parse dataset for game $gameId, skipping")
            } catch (e: Exception) {
                Napier.e(
                    message = "Error decoding dataset: $path, skipping",
                    throwable = e
                )
                continue
            }
        }
    }
}
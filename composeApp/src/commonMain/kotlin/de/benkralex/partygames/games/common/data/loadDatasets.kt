package de.benkralex.partygames.games.common.data

import de.benkralex.partygames.app.gamesRegister
import de.benkralex.partygames.app.getGameByGameId
import de.benkralex.partygames.games.common.domain.Dataset
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import partygames.composeapp.generated.resources.Res


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
    loadResourceDatasets(
        listOf(
            "files/find_liar/default.json",

            "files/impostor/default1.json",
            "files/impostor/default2.json",
            "files/impostor/default3.json",
            "files/impostor/default4.json",
            "files/impostor/default5.json",
            "files/impostor/default6.json",
            "files/impostor/default7.json",
            "files/impostor/default8.json",
            "files/impostor/default9.json",
            "files/impostor/default10.json",
        )
    )
}

suspend fun loadResourceDatasets(paths: List<String>) {
    for (path in paths) {
        val bytes = Res.readBytes(path)
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
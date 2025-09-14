package de.benkralex.partygames.datasets

import de.benkralex.partygames.app.gamesRegister
import de.benkralex.partygames.app.getGameByGameId
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

suspend fun loadDatasets(basePath: String) {
    for (game in gamesRegister) {
        game.datasets.clear()
    }
    //loadRemoteJsonFiles()
    val paths = getJsonFiles(basePath)
    for (path in paths) {
        val bytes = getJsonFileContent(path)
        parseDataset(bytes, path)
    }
    loadAllResourceDatasets()
}

fun parseDataset(bytes: ByteArray, path: String = ""): Dataset? {
    if (bytes.isNotEmpty()) {
        try {
            val jsonString = bytes.decodeToString()
            val json: JsonObject = Json.parseToJsonElement(jsonString) as JsonObject
            val gameId = json["game"]?.jsonPrimitive?.content ?: return null
            val game = getGameByGameId(gameId) ?: return null
            game.parseData(json)?.let { dataset ->
                Napier.i("Loaded dataset for game $gameId")
                if (game.datasets.any { it.uid == dataset.uid }) {
                    Napier.w("Dataset with uid ${dataset.uid} already exists for game $gameId, skipping")
                } else {
                    game.datasets.add(dataset)
                    return dataset
                }
            } ?: Napier.e("Failed to parse dataset for game $gameId, skipping")
        } catch (e: Exception) {
            Napier.e(
                message = "Error decoding dataset: $path, skipping",
                throwable = e
            )
            return null
        }
    }
    return null
}
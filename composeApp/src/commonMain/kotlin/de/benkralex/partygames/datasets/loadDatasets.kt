package de.benkralex.partygames.datasets

import de.benkralex.partygames.app.gamesRegister
import de.benkralex.partygames.app.getGameByGameId
import de.benkralex.partygames.games.common.domain.Game
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

suspend fun loadDatasets(basePath: String) {
    for (game in gamesRegister) {
        game.datasets.clear()
    }
    val paths = getJsonFiles(basePath)
    for (path in paths) {
        val bytes = getJsonFileContent(path)
        parseDataset(bytes, path)
    }
    loadAllResourceDatasets()
}

/**
 * Parse a dataset from raw JSON bytes and associate it with the corresponding game.
 *
 * Attempts to decode the provided bytes as JSON, extract the `game` id, find the matching
 * registered game, and delegate to the game-specific parsing routine.
 *
 * @param bytes Raw JSON file content to parse into a dataset.
 * @param path Optional source path used in error messages and logs.
 * @return The parsed Dataset if successful, `null` otherwise.
 */
fun parseDataset(bytes: ByteArray, path: String = ""): Dataset? {
    if (bytes.isNotEmpty()) {
        try {
            val jsonString = bytes.decodeToString()
            val json: JsonObject = Json.parseToJsonElement(jsonString) as JsonObject
            val gameId = json["game"]?.jsonPrimitive?.content ?: return null
            val game = getGameByGameId(gameId) ?: return null
            return parseDataset(json, game, gameId)
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

/**
 * Parse a dataset JSON for the given game and register it if parsing succeeds and the UID is unique.
 *
 * @param json The dataset JSON object to parse.
 * @param game The target game used to parse and store the dataset.
 * @param gameId The game's identifier used in logging and to indicate which game the dataset belongs to.
 * @return The parsed and registered `Dataset` when parsing succeeds and no dataset with the same `uid` already exists for the game, `null` otherwise.
 */
fun <D : Dataset> parseDataset(json: JsonObject, game: Game<*, D>, gameId: String): Dataset? {
    game.parseData(json)?.let { dataset ->
        Napier.i("Loaded dataset for game $gameId")
        if (game.datasets.any { it.uid == dataset.uid }) {
            Napier.w("Dataset with uid ${dataset.uid} already exists for game $gameId, skipping")
        } else {
            game.datasets.add(dataset)
            return dataset
        }
    } ?: Napier.e("Failed to parse dataset for game $gameId, skipping")
    return null
}
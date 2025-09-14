package de.benkralex.partygames.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive

class DatasetDownloader {
    companion object {
        const val DATASET_COLLECTION_FILENAME = "dataset-collection.json"
        suspend fun download(
            url: String,
            fileCallback: (String, ByteArray) -> Unit,
        ) {
            var url = url.trim()
            if (url.isBlank()) return
            if (!url.endsWith(".json")) {
                if (!url.endsWith("/")) url += "/"
                url += DATASET_COLLECTION_FILENAME
            }
            if (url.endsWith(DATASET_COLLECTION_FILENAME)) {
                val json: JsonElement = try {
                    getJSONFile(url)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return
                }
                val baseUrl = url.removeSuffix(DATASET_COLLECTION_FILENAME)
                val filesUrls = (json as JsonArray).toSet().map { baseUrl + it.jsonPrimitive.content }
                filesUrls.forEach {
                    try {
                        fileCallback(it, getJSONFile(it).toString().toByteArray())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else if (url.endsWith(".json")) {
                try {
                    fileCallback(url, getJSONFile(url).toString().toByteArray())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                throw IllegalArgumentException("URL must end with dataset-collection.json or .json")
            }
        }

        suspend fun getJSONFile(url: String): JsonElement {
            if (!url.endsWith(".json")) throw IllegalArgumentException("URL must end with .json")
            val response: ByteArray = try {
                val client = HttpClient()
                val response = client.get(url)
                response.body()
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
            val jsonString = response.decodeToString()
            return Json.decodeFromString(jsonString)
        }
    }
}
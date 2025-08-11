package de.benkralex.partygames.games.impostor.data

import de.benkralex.partygames.games.common.domain.Difficulty
import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.common.domain.difficultyFromNumber
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import partygames.composeapp.generated.resources.Res

data class ImpostorDataset(
    val uid: String,
    val title: TranslatableString,
    val description: TranslatableString,
    val author: TranslatableString,
    val topics: Map<String, TranslatableString>,
    val wordPairs: List<ImpostorWordPair>,
)

data class ImpostorWordPair(
    val mainWord: TranslatableString,
    val impostorHintWord: TranslatableString,
    val topic: TranslatableString,
    val difficulty: Difficulty,
)

var datasets: MutableList<ImpostorDataset> = mutableListOf()
suspend fun updateImpostorDatasets() {
    val paths: List<String> = listOf(
        "files/impostor/default/default1.json",
        /*"files/impostor/default/default2.json",
        "files/impostor/default/default3.json",
        "files/impostor/default/default4.json",
        "files/impostor/default/default5.json",
        "files/impostor/default/default6.json",
        "files/impostor/default/default7.json",
        "files/impostor/default/default8.json",
        "files/impostor/default/default9.json",
        "files/impostor/default/default10.json",*/
    )
    for (path in paths) {
        val bytes = Res.readBytes(path)
        if (bytes.isNotEmpty()) {
            try {
                val jsonDataset: JsonElement
                try {
                    val jsonString = bytes.decodeToString()
                    jsonDataset = Json.parseToJsonElement(jsonString) as JsonObject
                } catch (e: Exception) {
                    Napier.e(
                        message = "Error decoding Impostor dataset: $path",
                        throwable = e
                    )
                    continue
                }
                val topics: Map<String, TranslatableString> = (jsonDataset["topics"] as JsonObject).map { topic ->
                    topic.key to TranslatableString(
                        translations = (topic.value as JsonObject).map {
                            it.key to (it.value as JsonPrimitive).content
                        }.toMap()
                    )
                }.toMap()
                val dataset = ImpostorDataset(
                    uid = (jsonDataset["uid"] as JsonPrimitive).content,
                    title = TranslatableString(
                        translations = (jsonDataset["title"] as JsonObject).map {
                            it.key to (it.value as JsonPrimitive).content
                        }.toMap()
                    ),
                    description = TranslatableString(
                        translations = (jsonDataset["description"] as JsonObject).map {
                            it.key to (it.value as JsonPrimitive).content
                        }.toMap()
                    ),
                    author = TranslatableString(
                        translations = (jsonDataset["author"] as JsonObject).map {
                            it.key to (it.value as JsonPrimitive).content
                        }.toMap()
                    ),
                    topics = topics,
                    wordPairs = (jsonDataset["word_pairs"] as JsonArray).map { w ->
                        val wordPair = w as JsonObject
                        ImpostorWordPair(
                            mainWord = TranslatableString(
                                translations = (wordPair["main_word"] as JsonObject).map {
                                    it.key to (it.value as JsonPrimitive).content
                                }.toMap()
                            ),
                            impostorHintWord = TranslatableString(
                                translations = (wordPair["impostor_hint_word"] as JsonObject).map {
                                    it.key to (it.value as JsonPrimitive).content
                                }.toMap()
                            ),
                            topic = topics.getOrElse(
                                key = wordPair["topic_key"]?.jsonPrimitive?.content ?: "default",
                                defaultValue = { TranslatableString() }
                            ),
                            difficulty = difficultyFromNumber(
                                number = (wordPair["difficulty"] as JsonPrimitive).int
                            ),
                        )
                    }
                )
                datasets.add(dataset)
            } catch (e: Exception) {
                Napier.e(
                    message = "Error parsing Impostor dataset: $path",
                    throwable = e
                )
            }
        } else {
            Napier.e(
                message = "Error reading Impostor dataset: $path",
            )
        }
    }
}

fun getImposterWordSets(languages: List<String>): List<ImpostorWordPair> {
    return datasets.flatMap { it.wordPairs }.filter { q ->
        q.impostorHintWord.translations.keys.any { lang ->
            lang in languages
                    || lang.split("_")[0] in languages
                    || lang in languages.map { it.split("_")[0] }
        } && q.mainWord.translations.keys.any { lang ->
            lang in languages
                    || lang.split("_")[0] in languages
                    || lang in languages.map { it.split("_")[0] }
        }
    }
}

fun getImposterTopics(languages: List<String>): List<TranslatableString> {
    return getImposterWordSets(languages).map { it.topic }.toSet().toList()
}
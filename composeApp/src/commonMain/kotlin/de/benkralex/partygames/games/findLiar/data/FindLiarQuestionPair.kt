package de.benkralex.partygames.games.findLiar.data

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

data class FindLiarDataset(
    val uid: String,
    val title: TranslatableString,
    val description: TranslatableString,
    val author: TranslatableString,
    val topics: Map<String, TranslatableString>,
    val questionPairs: List<FindLiarQuestionPair>,
)

data class FindLiarQuestionPair(
    val mainQuestion: TranslatableString,
    val liarQuestion: TranslatableString,
    val topic: TranslatableString,
    val difficulty: Difficulty,
)

var datasets: MutableList<FindLiarDataset> = mutableListOf()
suspend fun updateQuestionDatasets() {
    val paths: List<String> = listOf(
        "files/find_liar/default.json",
        "files/find_liar/default1.json",
        "files/find_liar/default2.json",
        "files/find_liar/default3.json",
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
                        message = "Error decoding Find Liar dataset: $path",
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
                val dataset = FindLiarDataset(
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
                    questionPairs = (jsonDataset["questions"] as JsonArray).map { q ->
                        val question = q as JsonObject
                        FindLiarQuestionPair(
                            mainQuestion = TranslatableString(
                                translations = (question["main_question"] as JsonObject).map {
                                    it.key to (it.value as JsonPrimitive).content
                                }.toMap()
                            ),
                            liarQuestion = TranslatableString(
                                translations = (question["liar_question"] as JsonObject).map {
                                    it.key to (it.value as JsonPrimitive).content
                                }.toMap()
                            ),
                            topic = topics.getOrElse(
                                key = question["topic_key"]?.jsonPrimitive?.content ?: "default",
                                defaultValue = { TranslatableString() }
                            ),
                            difficulty = difficultyFromNumber(
                                number = (question["difficulty"] as JsonPrimitive).int
                            ),
                        )
                    }
                )
                datasets.add(dataset)
            } catch (e: Exception) {
                Napier.e(
                    message = "Error parsing Find Liar dataset: $path",
                    throwable = e
                )
            }
        } else {
            Napier.e(
                message = "Error reading Find Liar dataset: $path",
            )
        }
    }
}

fun getQuestionSets(languages: List<String>): List<FindLiarQuestionPair> {
    return datasets.flatMap { it.questionPairs }.filter { q ->
        q.liarQuestion.translations.keys.any { lang ->
            lang in languages
                    || lang.split("_")[0] in languages
                    || lang in languages.map { it.split("_")[0] }
        } && q.mainQuestion.translations.keys.any { lang ->
            lang in languages
                    || lang.split("_")[0] in languages
                    || lang in languages.map { it.split("_")[0] }
        }
    }
}

fun getTopics(languages: List<String>): List<TranslatableString> {
    return getQuestionSets(languages).map { it.topic }.toSet().toList()
}
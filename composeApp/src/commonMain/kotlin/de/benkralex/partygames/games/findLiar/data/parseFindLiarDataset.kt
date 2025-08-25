package de.benkralex.partygames.games.findLiar.data

import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.findLiar.domain.FindLiarDataset
import de.benkralex.partygames.games.findLiar.domain.FindLiarQuestionPair
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonPrimitive

fun parseFindLiarDataset(json: JsonObject): FindLiarDataset? {
    try {
        if (json["game"]?.jsonPrimitive?.content != "find_liar") {
            Napier.e(
                message = "Invalid Find Liar dataset",
            )
            return null
        }
        val topics: Map<String, TranslatableString> = (json["topics"] as JsonObject).map { topic ->
            topic.key to TranslatableString(
                translations = (topic.value as JsonObject).map {
                    it.key to (it.value as JsonPrimitive).content
                }.toMap()
            )
        }.toMap()
        val dataset = FindLiarDataset(
            uid = (json["uid"] as JsonPrimitive).content,
            title = TranslatableString(
                translations = (json["title"] as JsonObject).map {
                    it.key to (it.value as JsonPrimitive).content
                }.toMap()
            ),
            description = TranslatableString(
                translations = (json["description"] as JsonObject).map {
                    it.key to (it.value as JsonPrimitive).content
                }.toMap()
            ),
            author = TranslatableString(
                translations = (json["author"] as JsonObject).map {
                    it.key to (it.value as JsonPrimitive).content
                }.toMap()
            ),
            topics = topics,
            questionPairs = (json["questions"] as JsonArray).map { q ->
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
                    switchable = (question["switchable"] as JsonPrimitive).boolean,
                )
            }
        )
        return dataset
    } catch (e: Exception) {
        Napier.e(
            message = "Error parsing Find Liar dataset",
            throwable = e
        )
        return null
    }
}
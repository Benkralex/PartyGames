package de.benkralex.partygames.games.impostor.data

import de.benkralex.partygames.games.common.domain.TranslatableString
import de.benkralex.partygames.games.impostor.domain.ImpostorDataset
import de.benkralex.partygames.games.impostor.domain.ImpostorWordPair
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

fun parseImpostorDataset(json: JsonObject): ImpostorDataset? {
    if (json["game"]?.jsonPrimitive?.content != "impostor") {
        Napier.e(
            message = "Invalid Impostor dataset",
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
    val dataset = ImpostorDataset(
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
        wordPairs = (json["word_pairs"] as JsonArray).map { w ->
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
            )
        }
    )
    return dataset
}
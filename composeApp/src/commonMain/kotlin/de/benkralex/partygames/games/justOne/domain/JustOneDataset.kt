@file:UseSerializers(TranslatableStringSerializer::class)
package de.benkralex.partygames.games.justOne.domain

import de.benkralex.partygames.datasets.Dataset
import de.benkralex.partygames.games.common.domain.TranslatableString
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

val json = Json {
    ignoreUnknownKeys = true
}

@Serializable
data class JustOneDataset(
    override val uid: String,
    override var active: Boolean = true,
    override val title: TranslatableString,
    override val description: TranslatableString,
    override val author: TranslatableString,
    val topics: Map<String, TranslatableString>,
    val words: List<JustOneWord>,
) : Dataset

@Serializable
data class JustOneWord(
    val topic_key: String,
    val word: TranslatableString,
)

val translationMapSerializer = serializer<Map<String, String>>()

object TranslatableStringSerializer : KSerializer<TranslatableString> {
    override val descriptor: SerialDescriptor = translationMapSerializer.descriptor

    override fun serialize(
        encoder: Encoder,
        value: TranslatableString
    ) {
        translationMapSerializer.serialize(encoder, value.translations)
    }

    override fun deserialize(decoder: Decoder): TranslatableString {
        return TranslatableString(translationMapSerializer.deserialize(decoder))
    }
}

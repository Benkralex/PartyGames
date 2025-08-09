package de.benkralex.partygames.games.common.domain

import kotlinx.serialization.Serializable

@Serializable
data class TranslatableString(
    val translations: Map<String, String> = emptyMap(),
) {
    operator fun get(language: String): String {
        return translations[language] ?: translations[language.split("_")[0]] ?: ""
    }

    operator fun plus(other: TranslatableString): TranslatableString {
        return TranslatableString(translations + other.translations)
    }

    override fun toString(): String {
        return translations.toString()
    }
}

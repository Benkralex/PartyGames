package de.benkralex.partygames.games.common.domain

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.error_no_translation

@Serializable
data class TranslatableString(
    val translations: Map<String, String> = emptyMap(),
) {
    @Composable
    operator fun get(language: String): String {
        return translations[language]
            ?: translations[language.split("_")[0]]
            ?: translations[
                translations.keys.first {
                    it.split("_")[0] == language.split("_")[0]
                }
            ]
            ?: stringResource(Res.string.error_no_translation).replace("%lang%", language)
    }

    operator fun plus(other: TranslatableString): TranslatableString {
        return TranslatableString(translations + other.translations)
    }

    override fun toString(): String {
        return translations.toString()
    }
}

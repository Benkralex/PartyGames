package de.benkralex.partygames.games.impostor.domain

import de.benkralex.partygames.games.common.domain.TranslatableString

data class ImpostorWordPair(
    val mainWord: TranslatableString,
    val impostorHintWord: TranslatableString,
    val topic: TranslatableString,
)

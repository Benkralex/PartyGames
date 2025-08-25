package de.benkralex.partygames.games.findLiar.domain

import de.benkralex.partygames.games.common.domain.TranslatableString

data class FindLiarQuestionPair(
    val switchable: Boolean = true,
    val mainQuestion: TranslatableString,
    val liarQuestion: TranslatableString,
    val topic: TranslatableString,
)
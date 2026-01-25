package de.benkralex.partygames.games.findLiar.domain

import de.benkralex.partygames.games.common.domain.TranslatableString

data class FindLiarSettings(
    val players: List<String>,
    val liarCount: Int,
    val topics: List<TranslatableString>,
)
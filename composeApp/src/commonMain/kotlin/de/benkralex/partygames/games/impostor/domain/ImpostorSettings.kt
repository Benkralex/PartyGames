package de.benkralex.partygames.games.impostor.domain

import de.benkralex.partygames.games.common.domain.TranslatableString

data class ImpostorSettings(
    val players: List<String>,
    val impostorCount: Int,
    val topics: List<TranslatableString>,
    val hint: Any? = null,
)
package de.benkralex.partygames.games.impostor.domain

import de.benkralex.partygames.games.common.domain.TranslatableString

data class ImpostorDataset(
    val uid: String,
    val title: TranslatableString,
    val description: TranslatableString,
    val author: TranslatableString,
    val topics: Map<String, TranslatableString>,
    val wordPairs: List<ImpostorWordPair>,
)

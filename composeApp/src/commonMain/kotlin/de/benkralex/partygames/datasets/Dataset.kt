package de.benkralex.partygames.datasets

import de.benkralex.partygames.games.common.domain.TranslatableString

interface Dataset {
    val uid: String
    val title: TranslatableString
    val description: TranslatableString
    val author: TranslatableString
}
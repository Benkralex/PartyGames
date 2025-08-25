package de.benkralex.partygames.games.common.domain

interface Dataset {
    val uid: String
    val title: TranslatableString
    val description: TranslatableString
    val author: TranslatableString
}
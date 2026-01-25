package de.benkralex.partygames.lanParty.domain

data class Host (
    val hostName: String,
    val port: Int,
    val path: String,
    val protocol: String,
)
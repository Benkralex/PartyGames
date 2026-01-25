package de.benkralex.partygames.games.truthOrDare.domain

data class TruthOrDareSettings(
    val topics: List<String>,
    val ageMin: Int?,
    val ageMax: Int?,
)
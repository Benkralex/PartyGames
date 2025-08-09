package de.benkralex.partygames.games.common.domain

enum class Difficulty {
    TRIVIAL,
    EXTRA_EASY,
    EASY,
    MODERATE,
    MEDIUM,
    CHALLENGING,
    HARD,
    VERY_HARD,
    EXTREME,
    NIGHTMARE,
}

fun difficultyFromNumber(number: Int): Difficulty {
    return when (number) {
        1 -> Difficulty.TRIVIAL
        2 -> Difficulty.EXTRA_EASY
        3 -> Difficulty.EASY
        4 -> Difficulty.MODERATE
        5 -> Difficulty.MEDIUM
        6 -> Difficulty.CHALLENGING
        7 -> Difficulty.HARD
        8 -> Difficulty.VERY_HARD
        9 -> Difficulty.EXTREME
        10 -> Difficulty.NIGHTMARE
        else -> throw IllegalArgumentException("Invalid difficulty number: $number")
    }
}
package de.benkralex.partygames

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
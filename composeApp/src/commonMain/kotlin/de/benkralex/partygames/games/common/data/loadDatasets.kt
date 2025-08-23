package de.benkralex.partygames.games.common.data

expect fun getApplicationDataDirectory(): String

expect suspend fun getJsonFiles(basePath: String): List<String>

expect suspend fun getJsonFileContent(path: String): ByteArray
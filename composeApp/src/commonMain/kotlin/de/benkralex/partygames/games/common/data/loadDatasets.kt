package de.benkralex.partygames.games.common.data


expect suspend fun getJsonFiles(basePath: String, folder: String): List<String>

expect suspend fun getJsonFileContent(path: String): ByteArray
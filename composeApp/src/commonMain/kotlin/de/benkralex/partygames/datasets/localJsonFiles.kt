package de.benkralex.partygames.datasets

expect suspend fun getJsonFiles(basePath: String): List<String>

expect suspend fun getJsonFileContent(path: String): ByteArray
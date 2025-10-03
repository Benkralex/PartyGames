package de.benkralex.partygames.datasets

actual suspend fun getJsonFiles(basePath: String): List<String> {
    return emptyList()
}

actual suspend fun getJsonFileContent(path: String): ByteArray {
    return ByteArray(0)
}
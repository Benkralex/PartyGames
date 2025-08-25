package de.benkralex.partygames.games.common.data

import io.github.aakira.napier.Napier
import java.io.File

actual suspend fun getJsonFiles(basePath: String): List<String> {
    val path = basePath
    val paths = File(path)
        .walkTopDown()
        .filter { it.isFile && it.extension == "json" }
        .map { it.absolutePath }
        .toList()
    Napier.i("Found JSON files at: $paths")
    return paths
}

actual suspend fun getJsonFileContent(path: String): ByteArray {
    return File(path).readBytes()
}
package de.benkralex.partygames.games.common.data

import java.io.File

actual suspend fun getJsonFiles(basePath: String): List<String> {
   return File(basePath)
           .walkTopDown()
           .filter { it.isFile && it.extension == "json" }
           .map { it.absolutePath }
           .toList()
}

actual suspend fun getJsonFileContent(path: String): ByteArray {
    return File(path).readBytes()
}

actual fun getApplicationDataDirectory(): String {
    return File(System.getProperty("user.home"), ".partygames").apply {
        if (!exists()) {
            mkdirs()
        }
    }.absolutePath
}
package de.benkralex.partygames.games.common.data

import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import de.benkralex.partygames.MainActivity
import io.github.aakira.napier.Napier
import java.io.File

actual suspend fun getJsonFiles(
    basePath: String,
    folder: String,
): List<String> {
    val uri = "$basePath%2F$folder".toUri()
    return when (uri.scheme) {
        "content" -> {
            val ctx = MainActivity.instance
            val root = DocumentFile.fromTreeUri(ctx, uri) ?: DocumentFile.fromSingleUri(ctx, uri)
            if (root == null) emptyList() else {
                val result = mutableListOf<String>()
                fun walk(doc: DocumentFile) {
                    for (child in doc.listFiles()) {
                        if (child.isDirectory) {
                            walk(child)
                        } else if (child.isFile && child.name?.endsWith(".json", ignoreCase = true) == true) {
                            result += child.uri.toString()
                        }
                    }
                }
                if (root.isDirectory) {
                    walk(root)
                } else if (root.isFile && root.name?.endsWith(".json", ignoreCase = true) == true) {
                    result += root.uri.toString()
                }
                result
            }
        }
        "file", null -> {
            File(uri.path ?: basePath)
                .walkTopDown()
                .filter { it.isFile && it.extension.equals("json", ignoreCase = true) }
                .map { it.absolutePath }
                .toList()
        }
        else -> emptyList()
    }
}

actual suspend fun getJsonFileContent(path: String): ByteArray {
    val uri = path.toUri()
    val bytes: ByteArray
    when (uri.scheme) {
        "content" -> {
            val ctx = MainActivity.instance
            bytes = ctx.contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: ByteArray(0)
        }
        "file", null -> {
            bytes = File(uri.path ?: path).readBytes()
        }
        else -> {
            bytes = ByteArray(0)
        }
    }
    Napier.d("Path: $path\n Content:\n${bytes}")
    return bytes
}
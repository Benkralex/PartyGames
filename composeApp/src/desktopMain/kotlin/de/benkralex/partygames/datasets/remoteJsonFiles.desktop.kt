package de.benkralex.partygames.datasets

import java.io.File

actual fun saveFile(path: String, fileName: String, bytes: ByteArray) {
    val dir = File(path)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val file = File(dir, fileName)
    file.writeBytes(bytes)
}
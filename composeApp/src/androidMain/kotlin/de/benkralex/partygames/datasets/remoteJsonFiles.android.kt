package de.benkralex.partygames.datasets

import android.provider.DocumentsContract
import androidx.core.net.toUri
import de.benkralex.partygames.MainActivity
import io.github.aakira.napier.Napier
import kotlinx.io.IOException
import kotlinx.io.files.FileNotFoundException
import java.io.FileOutputStream

actual fun saveFile(path: String, fileName: String, bytes: ByteArray) {
    val uri = DocumentsContract.buildDocumentUriUsingTree(path.toUri(), fileName)
    Napier.d("Saving file to $uri")
    val contentResolver = MainActivity.instance.contentResolver
    try {
        contentResolver.openFileDescriptor(uri, "w")?.use { f ->
            FileOutputStream(f.fileDescriptor).use {
                it.write(bytes)
            }
        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
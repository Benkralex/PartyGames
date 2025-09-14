package de.benkralex.partygames.datasets

import de.benkralex.partygames.networking.DatasetDownloader
import de.benkralex.partygames.settingsPage.data.settings
import io.github.aakira.napier.Napier

expect fun saveFile(path: String, fileName: String, bytes: ByteArray)

suspend fun loadRemoteJsonFiles() {
    settings.value.datasetUrls.forEach {
        DatasetDownloader.download(
            it,
        ) { url, bytes ->
            Napier.d(
                "Downloaded dataset from $url, size: ${bytes.size}, content: ${
                    bytes.decodeToString().take(100)
                }"
            )
            parseDataset(bytes, url)
            if (settings.value.datasetPath.isNotBlank()) {
                Napier.d("Saving dataset (${url.substringAfterLast("/")}) to ${settings.value.datasetPath}")
                saveFile(settings.value.datasetPath, url.substringAfterLast('/'), bytes)
            }
        }
    }
}
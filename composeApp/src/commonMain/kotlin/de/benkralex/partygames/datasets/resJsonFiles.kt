package de.benkralex.partygames.datasets

import io.github.aakira.napier.Napier
import partygames.composeapp.generated.resources.Res

suspend fun loadResourceDatasets(paths: List<String>) {
    for (path in paths) {
        val bytes = Res.readBytes(path)
        Napier.d("Path: $path, size: ${bytes.size}, content: ${bytes.decodeToString().take(100)}")
        parseDataset(bytes, path)
    }
}

suspend fun loadAllResourceDatasets() {
    loadResourceDatasets(
        listOf(
            "files/find_liar/default.json",

            "files/impostor/default.json",
        )
    )
}
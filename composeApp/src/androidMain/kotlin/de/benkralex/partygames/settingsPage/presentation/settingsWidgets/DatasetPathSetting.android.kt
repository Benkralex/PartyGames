package de.benkralex.partygames.settingsPage.presentation.settingsWidgets

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable

private const val REQUEST_TREE = 1001

internal object DatasetPathCallbackHolder {
    var callback: ((String) -> Unit)? = null
}

@Composable
actual fun OpenFilePicker(onPathSelected: (String) -> Unit) {
    val activity = LocalActivity.current ?: return
    DatasetPathCallbackHolder.callback = onPathSelected

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
        addFlags(
            Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
                    Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        )
    }
    activity.startActivityForResult(intent, REQUEST_TREE)
}
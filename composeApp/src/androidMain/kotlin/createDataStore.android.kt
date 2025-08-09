import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import de.benkralex.partygames.app.DATA_STORE_FILE_NAME
import de.benkralex.partygames.app.createDataStore

fun createDataStore(context: Context): DataStore<Preferences> {
    return createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}
package de.benkralex.partygames

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import createDataStore
import de.benkralex.partygames.app.App
import de.benkralex.partygames.settingsPage.presentation.settingsWidgets.DatasetPathCallbackHolder


class MainActivity : ComponentActivity() {

    companion object {
        lateinit var instance: MainActivity
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        instance = this

        setContent {
            MaterialTheme {
                App(
                    prefs = remember {
                        createDataStore(applicationContext)
                    },
                    theme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)  {
                        val context = LocalContext.current
                        if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
                    } else {
                        null
                    },
                )
            }
        }
    }

    @SuppressLint("WrongConstant")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data ?: return
            val flags: Int = data.flags and
                    (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            contentResolver.takePersistableUriPermission(uri, flags)
            DatasetPathCallbackHolder.callback?.invoke(uri.toString())
            DatasetPathCallbackHolder.callback = null
        }
    }
}
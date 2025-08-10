package de.benkralex.partygames

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
import createDataStore
import de.benkralex.partygames.app.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

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
                    }
                )
            }
        }
    }
}
package de.benkralex.partygames.lanParty.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.benkralex.partygames.lanParty.data.getIPAddress
import de.benkralex.partygames.lanParty.domain.IPAddress
import de.benkralex.partygames.lanParty.domain.startServer
import io.github.aakira.napier.Napier

class LanPartyHostViewModel : ViewModel() {
    var port: Int by mutableStateOf(3333)
    val portStr: String by derivedStateOf { if (port != 0) port.toString() else "" }
    val isPortError: Boolean by derivedStateOf { port !in 1..65535 }

    val ipAddress: IPAddress = getIPAddress()

    var hostPlays: Boolean by mutableStateOf(true)

    var name: String by mutableStateOf("")

    val noErrors: Boolean by derivedStateOf {
        !isPortError && (!name.isBlank() || !hostPlays)
    }
    var startingServer: Boolean by mutableStateOf(false)

    fun changePort(newPort: String) {
        if (newPort.all { c -> c.isDigit() } && newPort.length <= 6) {
            port = if (newPort.isNotBlank()) {
                newPort.toInt()
            } else {
                0
            }
        }
    }

    fun startLanParty(
        onSuccess: () -> Unit = {},
        onError: () -> Unit = {},
    ) {
        Napier.d("IP: $ipAddress:$port")
        if (hostPlays) Napier.d("Name: ${name.trim()}")
        startingServer = true
        startServer(
            port = port,
            onSuccess = {
                startingServer = false
                onSuccess()
            },
            onError = {
                startingServer = false
                onError()
            },
        )
    }
}
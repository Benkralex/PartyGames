package de.benkralex.partygames.lanParty.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.benkralex.partygames.app.PLATFORM
import de.benkralex.partygames.app.networkManager
import de.benkralex.partygames.lanParty.domain.Host

class LanPartyJoinViewModel : ViewModel() {
    var name: String by mutableStateOf("")
    val nameValid: Boolean by derivedStateOf {
        Regex("[a-zA-z0-9_-]{3,10}").matches(name)
    }
    val nameUsed: Boolean by derivedStateOf {
        name in networkManager.players
    }

    var hostString: String by mutableStateOf("")
    val hostStringValid: Boolean by derivedStateOf {
        Regex("^wss?://([a-zA-Z0-9.\\-_]+):([0-9]{1,5})(/.*)?$").matches(hostString)
    }

    val isConnected: Boolean by derivedStateOf {
        networkManager.host != null
    }
    val canJoin: Boolean by derivedStateOf {
        !nameUsed && nameValid && networkManager.host != null && !networkManager.active
    }

    fun join() {
        if (!canJoin) return
        networkManager.join(name)
    }

    fun setHost(host: String) {
        if (!Regex("^wss?://([a-zA-Z0-9.\\-_]+):([0-9]{1,5})(/.*)?$").matches(host)) return
        val protocol = if (host.startsWith("ws://")) "ws" else "wss"
        val hostName = host.substring(protocol.length + 3).split(":")[0]
        val port = host.substring(protocol.length + 3 + hostName.length + 1).split("/")[0]
        val path = host.substring(protocol.length + 3 + hostName.length + 1 + port.length)
        networkManager.host = Host(
            hostName = hostName,
            port = port.toInt(),
            path = path,
            protocol = protocol
        )
        networkManager.createConnection()
    }
}
package de.benkralex.partygames.lanParty.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.benkralex.partygames.app.gamesRegister
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NetworkManager {
    var host: Host? by mutableStateOf(null)
    var name: String by mutableStateOf("")

    private val client = HttpClient {
        install(WebSockets)
    }

    private var session: WebSocketSession? = null
    private var connectionJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    var players: Set<String> by mutableStateOf(emptySet())
    var active: Boolean by mutableStateOf(false)

    private val outgoingMessages = Channel<String>(Channel.BUFFERED)

    fun createConnection() {
        if (connectionJob != null) return
        connectionJob = scope.launch {
            try {
                client.webSocket(
                    host = host?.hostName,
                    port = host?.port,
                    path = host?.path
                ) {
                    session = this

                    // Launch message sender
                    val senderJob = launch {
                        for (message in outgoingMessages) {
                            send(Frame.Text(message))
                        }
                    }

                    // Receive messages
                    try {
                        for (frame in incoming) {
                            if (frame is Frame.Text) {
                                val text = frame.readText()
                                onReceiveMessage(text)
                            }
                        }
                    } finally {
                        senderJob.cancel()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                reset()
            }
        }
    }

    fun join(name: String) {
        if (!Regex("[a-zA-z0-9_-]{3,10}").matches(name)) return
        if (name in players) return
        this.name = name
        sendMessage("${MsgPrefixes.JOIN}$name")
    }

    fun sendGames() {
        for (game in gamesRegister) {
            sendMessage("${MsgPrefixes.GAME_IN_CLIENT}${game.gameId}")
        }
    }

    fun sendMessage(message: String) {
        scope.launch {
            outgoingMessages.send(message)
        }
    }

    private fun onReceiveMessage(msg: String) {
        Napier.d("Received Message: $msg")
        if (msg.startsWith(MsgPrefixes.NEW_PLAYER)) {
            val playerName = msg.substring(MsgPrefixes.NEW_PLAYER.length)
            players += playerName
        } else if (msg.startsWith(MsgPrefixes.PLAYER_LEFT)) {
            val playerName = msg.substring(MsgPrefixes.PLAYER_LEFT.length)
            players -= playerName
            if (playerName == name) {
                active = false
                name = ""
            }
        } else if (msg.startsWith(MsgPrefixes.NAME_TAKEN)) {
            players += name
        } else if (msg.startsWith(MsgPrefixes.WELCOME)) {
            active = true
            sendGames()
            Napier.d("Connected successfully with name $name")
        }
    }

    private fun reset() {
        active = false
        players = emptySet()
        name = ""
        host = null
        session = null
        connectionJob = null
    }

    fun disconnect() {
        scope.launch {
            sendMessage(MsgPrefixes.LEAVE)
            session?.close()
            connectionJob?.cancel()
            reset()
        }
    }

    fun close() {
        disconnect()
        client.close()
    }
}
package de.benkralex.partygames.lanParty.domain

import io.github.aakira.napier.Napier
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Collections
import kotlin.time.Duration.Companion.seconds

private var server: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>? = null
private val connections = Collections.synchronizedSet<WebSocketSession>(LinkedHashSet())

private lateinit var manager: ServerManager<WebSocketSession>

actual fun getServerManager(): ServerManager<*>? {
    return manager
}

actual fun startServer(
    port: Int,
    onSuccess: () -> Unit,
    onError: () -> Unit,
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            manager = ServerManager(
                sendMessageCall = { conn, msg ->
                    CoroutineScope(Dispatchers.IO).launch {
                        conn.send(msg)
                    }
                },
                broadcastToAll = { msg ->
                    CoroutineScope(Dispatchers.IO).launch {
                        connections.forEach { it.send(msg) }
                    }
                }
            )
            server = embeddedServer(Netty, port = port) {
                install(WebSockets) {
                    pingPeriod = 15.seconds
                    timeout = 15.seconds
                    maxFrameSize = Long.MAX_VALUE
                    masking = false
                }
                routing {
                    webSocket("/game") {
                        connections += this
                        Napier.d("New WebSocket connection established. Total connections: ${connections.size}")
                        manager.sendPlayers(this)
                        try {
                            for (frame in incoming) {
                                when (frame) {
                                    is Frame.Text -> {
                                        val text = frame.readText()
                                        Napier.d("Received message: $text")
                                        manager.handleMessage(this, text)
                                    }

                                    is Frame.Close -> {
                                        Napier.d("WebSocket connection closed")
                                        manager.leave(this)
                                    }

                                    else -> {}
                                }
                            }
                        } catch (e: Exception) {
                            Napier.e("WebSocket error: ${e.message}", e)
                        } finally {
                            manager.leave(this)
                            connections -= this
                            Napier.d("Connection removed. Remaining connections: ${connections.size}")
                        }
                    }
                }
            }.start(wait = false)

            // Wait a bit for the server to be fully ready to accept connections
            kotlinx.coroutines.delay(100)

            Napier.d("WebSocket server started on port $port")
            onSuccess()
        } catch (e: Exception) {
            onError()
            Napier.e(
                throwable = e,
                message = "Error Starting Server",
            )
        }
    }
}

actual fun stopServer() {
    server?.stop(1000, 2000)
    server = null
    connections.clear()
    Napier.d("WebSocket server stopped")
}
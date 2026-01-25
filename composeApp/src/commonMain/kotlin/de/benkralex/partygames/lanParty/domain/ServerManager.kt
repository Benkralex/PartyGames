package de.benkralex.partygames.lanParty.domain

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap

class ServerManager<T>(
    val sendMessageCall: (T, String) -> Unit,
    val broadcastToAll: (String) -> Unit,
) {
    val connections: SnapshotStateMap<T, String> = mutableStateMapOf()
    val gamesInClients: MutableMap<T, MutableSet<String>> = mutableMapOf<T, MutableSet<String>>().withDefault { mutableSetOf() }

    fun sendMessage(connection: T, msg: String) {
        sendMessageCall(connection, msg)
    }

    fun sendMessage(name: String, msg: String) {
        getConnection(name)?.let {
            sendMessage(it, msg)
        }
    }

    fun getConnection(name: String): T? {
        for (entry in connections) {
            if (entry.value == name) {
                return entry.key
            }
        }
        return null
    }

    fun broadcastMessage(msg: String) {
        for (name in connections.values) {
            sendMessage(name, msg)
        }
    }

    fun handleMessage(connection: T, msg: String) {
        if (msg.startsWith(MsgPrefixes.JOIN)) {
            val name = msg.substring(MsgPrefixes.JOIN.length)
            val joinResult = join(name, connection)
            when (joinResult) {
                JoinResult.SUCCESS -> {
                    sendMessage(connection, MsgPrefixes.WELCOME)
                    broadcastToAll("${MsgPrefixes.NEW_PLAYER}$name")
                }
                JoinResult.NAME_TAKEN -> {
                    sendMessage(connection, MsgPrefixes.NAME_TAKEN)
                }
                JoinResult.NAME_INVALID -> {
                    sendMessage(connection, MsgPrefixes.NAME_INVALID)
                }
            }
        } else if (msg.startsWith(MsgPrefixes.LEAVE)) {
            val name = msg.substring(MsgPrefixes.LEAVE.length)
            for (entry in connections) {
                if (entry.value == name) {
                    leave(entry.key)
                    return
                }
            }
        } else if (msg.startsWith(MsgPrefixes.GAME_IN_CLIENT)) {
            val gameId = msg.substring(MsgPrefixes.GAME_IN_CLIENT.length)
            if (isGameIdValid(gameId)) {
                if (gamesInClients[connection] == null) {
                    gamesInClients[connection] = mutableSetOf(gameId)
                } else {
                    gamesInClients[connection]!!.add(gameId)
                }
            }
        } else if (msg.startsWith(MsgPrefixes.CHECK_NAME_FREE)) {
            val name = msg.substring(MsgPrefixes.CHECK_NAME_FREE.length)
            if (isNameValid(name) && !isNameInUse(name)) {
                sendMessage(connection, MsgPrefixes.NAME_FREE)
            } else {
                sendMessage(connection, MsgPrefixes.NAME_TAKEN)
            }
        }
    }

    fun join(name: String, connection: T): JoinResult {
        if (!isNameValid(name)) return JoinResult.NAME_INVALID
        if (isNameInUse(name)) return JoinResult.NAME_TAKEN
        connections[connection] = name
        return JoinResult.SUCCESS
    }

    fun leave(connection: T) {
        val name = connections[connection] ?: return
        connections.remove(connection)
        gamesInClients.remove(connection)
        broadcastToAll("${MsgPrefixes.PLAYER_LEFT}${name}")
    }

    fun leave(name: String) {
        getConnection(name)?.let {
            leave(it)
        }
    }

    fun sendPlayers(connection: T) {
        for (name in connections.values) {
            if (name == connections[connection]) continue
            sendMessage(connection, "${MsgPrefixes.NEW_PLAYER}$name")
        }
    }

    fun canPlayGame(gameId: String): Boolean {
        return gamesInClients.values.all { gameId in it }
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotBlank() && Regex("[a-zA-z0-9_-]{3,10}").matches(name)
    }

    private fun isNameInUse(name: String): Boolean {
        return connections.values.any { it == name }
    }

    private fun isGameIdValid(gameId: String): Boolean {
        return gameId.isNotBlank() && Regex("[a-zA-z_]+").matches(gameId)
    }
}

enum class JoinResult {
    SUCCESS,
    NAME_TAKEN,
    NAME_INVALID,
}

expect fun getServerManager(): ServerManager<*>?

expect fun startServer(
    port: Int,
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {},
)

expect fun stopServer()
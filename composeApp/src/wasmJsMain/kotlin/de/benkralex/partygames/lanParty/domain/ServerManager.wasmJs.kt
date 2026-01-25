package de.benkralex.partygames.lanParty.domain

actual fun getServerManager(): ServerManager<*>? {
    return null
}
actual fun startServer(
    port: Int,
    onSuccess: () -> Unit,
    onError: () -> Unit,
) {}

actual fun stopServer() {}
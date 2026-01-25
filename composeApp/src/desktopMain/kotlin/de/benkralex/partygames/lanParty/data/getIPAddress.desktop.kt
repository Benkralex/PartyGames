package de.benkralex.partygames.lanParty.data

import de.benkralex.partygames.lanParty.domain.IPAddress
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

actual fun getIPAddress(): IPAddress {
    try {
        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val networkInterface = interfaces.nextElement()

            // Skip loopback interfaces (like 127.0.0.1) and inactive interfaces
            if (networkInterface.isLoopback || !networkInterface.isUp) {
                continue
            }

            val addresses = networkInterface.inetAddresses
            while (addresses.hasMoreElements()) {
                val address = addresses.nextElement()

                // Check for IPv4 address (exclude IPv6)
                if (address is Inet4Address) {
                    val bytes = address.hostAddress.split(".").map { it.toInt().toUByte() }
                    return IPAddress(bytes[0], bytes[1], bytes[2], bytes[3])
                }
            }
        }
    } catch (e: SocketException) {
        e.printStackTrace()
    }
    return IPAddress(0,0,0,0)
}
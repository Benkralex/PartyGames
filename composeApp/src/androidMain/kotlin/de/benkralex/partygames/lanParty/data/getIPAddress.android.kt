package de.benkralex.partygames.lanParty.data

import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat
import de.benkralex.partygames.MainActivity
import de.benkralex.partygames.lanParty.domain.IPAddress

actual fun getIPAddress(): IPAddress {
    val wifiManager = ContextCompat.getSystemService(
        MainActivity.instance,
        WifiManager::class.java
    )
    val ipInt = wifiManager?.connectionInfo?.ipAddress ?: 0
    return IPAddress(ipInt and 0xff, ipInt shr 8 and 0xff, ipInt shr 16 and 0xff, ipInt shr 24 and 0xff)
}
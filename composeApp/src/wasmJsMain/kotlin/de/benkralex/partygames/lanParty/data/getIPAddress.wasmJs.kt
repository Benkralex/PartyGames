package de.benkralex.partygames.lanParty.data

import de.benkralex.partygames.lanParty.domain.IPAddress

actual fun getIPAddress(): IPAddress {
    return IPAddress(0, 0, 0, 0)
}
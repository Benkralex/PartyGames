package de.benkralex.partygames.lanParty.domain

class MsgPrefixes {
    companion object {
        const val LEAVE = "/leave"
        const val JOIN = "/join "
        const val WELCOME = "/welcome"
        const val NEW_PLAYER = "/newPlayer "
        const val PLAYER_LEFT = "/playerLeft "
        const val NAME_TAKEN = "/nameTaken"
        const val NAME_INVALID = "/nameInvalid"
        const val GAME_IN_CLIENT = "/gameInClient "
        const val CHECK_NAME_FREE = "/checkNameFree "
        const val NAME_FREE = "/nameFree"
    }
}
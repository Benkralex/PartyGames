package de.benkralex.partygames.lanParty.domain

class IPAddress(
    var firstByte: UByte,
    var secondByte: UByte,
    var thirdByte: UByte,
    var fourthByte: UByte,
) {
    constructor(
        firstByteInt: Int,
        secondByteInt: Int,
        thirdByteInt: Int,
        fourthByteInt: Int,
    ) : this(
        firstByte = firstByteInt.apply { require(this in 0..255) { "Byte 1 !in 0-255" } }.toUByte(),
        secondByte = secondByteInt.apply { require(this in 0..255) { "Byte 2 !in 0-255" } }.toUByte(),
        thirdByte = thirdByteInt.apply { require(this in 0..255) { "Byte 3 !in 0-255" } }.toUByte(),
        fourthByte = fourthByteInt.apply { require(this in 0..255) { "Byte 4 !in 0-255" } }.toUByte(),
    )

    override fun toString(): String {
        return listOf(firstByte, secondByte, thirdByte, fourthByte).joinToString(".") {
            it.toInt().toString()
        }
    }
}
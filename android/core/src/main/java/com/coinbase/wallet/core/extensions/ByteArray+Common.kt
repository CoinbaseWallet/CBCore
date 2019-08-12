package com.coinbase.wallet.core.extensions

import android.util.Base64

private val hexadecimalArray = "0123456789abcdef".toCharArray()

/**
 * Convert ByteArray to base64 String
 */
fun ByteArray.base64EncodedString(): String {
    return Base64.encodeToString(this, Base64.NO_WRAP)
}

/**
 * Convert to prefixed hex string
 */
fun ByteArray.toPrefixedHexString(): String {
    if (isEmpty()) {
        return "0x"
    }

    val startIndex = 2
    val outputLength = size * 2 + startIndex
    val output = CharArray(outputLength)

    output[0] = '0'
    output[1] = 'x'

    var i = startIndex
    for (b in this.toTypedArray()) {
        val left = (b.toInt() and 255) / 16
        val right = (b.toInt() and 255) % 16
        output[i] = hexadecimalArray[left]
        output[i + 1] = hexadecimalArray[right]
        i += 2
    }
    return String(output)
}

/**
 * Returns null if the decoded string contains an unsupported character
 */
val ByteArray.asUTF8String: String? get() {
    val decoded = this.toString(Charsets.UTF_8)

    for (char in decoded.toCharArray()) {
        if (!isCharSupported(char.toInt())) return null
    }

    return decoded
}

private fun isCharSupported(char: Int): Boolean {
    return when (char) {
        in 32..160,
        in 161..399,
        in 417..431,
        in 432..496,
        in 507..536,
        in 537..567,
        in 711..713,
        in 729..755,
        in 769..771,
        in 901..908,
        in 911..931,
        in 932..977,
        in 978..982,
        in 1025..1160,
        in 1161..7680,
        in 7681..7742,
        in 7743..7808,
        in 7809..7840,
        in 7841..8013,
        in 8193..8208,
        in 8209..8211,
        in 8212..8215,
        in 8216..8224,
        in 8225..8229,
        in 8230..8240,
        in 8243..8249,
        in 8250..8252,
        in 8356..8358,
        in 8359..8369,
        in 8378..8380,
        in 8381..8453,
        in 8540..8706,
        in 8722..8730,
        in 8805..9674,
        in 60930..63171,
        in 64258..65279,
        in 65533..65534 -> true
        else -> false
    }
}

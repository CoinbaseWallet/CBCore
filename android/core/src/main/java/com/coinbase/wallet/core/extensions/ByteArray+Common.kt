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
 * Convert ByteArray to hex encoded string
 */
fun ByteArray.toHexString(): String {
    val result = StringBuffer()
    for (byt in this) {
        val hex = Integer.toString((byt and 0xff) + 0x100, 16).substring(1)
        result.append(hex)
    }

    return result.toString()
}

private infix fun Byte.and(value: Int) = toInt() and value

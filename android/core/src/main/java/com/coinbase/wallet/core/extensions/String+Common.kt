package com.coinbase.wallet.core.extensions

import com.coinbase.wallet.core.util.JSON
import com.squareup.moshi.Types
import java.math.BigInteger
import java.net.URL

private const val hexadecimalCharacters = "0123456789abcdef"

/**
 * Determine whether string is a valid hex string
 */
val String.isHexString: Boolean
    get() = try {
        Regex("^(0x|0X)[a-f0-9]*$", RegexOption.IGNORE_CASE).matches(this)
    } catch (e: Exception) {
        false
    }

/**
 * Convert optional string to URL if possible
 */
val String.asURL: URL? get() = try { URL(this) } catch (e: Throwable) { null }

/**
 * Helper function to convert optional string to a big integer
 */
val String?.asBigInteger: BigInteger? get() = this?.let { BigInteger(it) }

/**
 * Strip out "0x" prefix if one exists. Otherwise, no-op
 */
fun String.strip0x(): String {
    return if (startsWith("0x")) {
        this.substring(2)
    } else {
        this
    }
}

/**
 * Convert to hex Data if possible
 */
fun String.asHexEncodedData(): ByteArray? {
    val str = this.strip0x().toLowerCase().let { if (it.length % 2 == 0) { it } else { "0$it" } }
    val size = str.length / 2
    val bytes = ByteArray(size)

    for (i in 0 until size) {
        val valLeft = hexadecimalCharacters.indexOf(str[i * 2])
        if (valLeft == -1) {
            return null
        }
        val valRight = hexadecimalCharacters.indexOf(str[i * 2 + 1])
        if (valRight == -1) {
            return null
        }
        bytes[i] = (valLeft * 16 + valRight).toByte()
    }

    return bytes
}

/**
 * Convert JSON string to Map<String, Any>
 */
fun String.asJsonMap(): Map<String, Any>? {
    val type = Types.newParameterizedType(Map::class.java, String::class.java, Any::class.java)
    val adapter = JSON.moshi.adapter<Map<String, Any>>(type)

    return adapter.fromJson(this)
}

/**
 * Truncates the middle of a string if necessary. Removes leading and trailing whitespace.
 * If the truncated string would be longer than the original string, the original String is returned.
 */
fun String.truncateMiddle(
    charactersAtFront: Int,
    charactersAtEnd: Int,
    truncationString: String = "…"
): String {

    val trimmed = this.trim()

    if (trimmed.length <= charactersAtFront + charactersAtEnd + truncationString.length) {
        return this
    }

    val startOfEnd = trimmed.lastIndex - charactersAtEnd + 1

    val firstChars = trimmed.substring(0, charactersAtFront)
    val lastChars = trimmed.substring(startOfEnd)

    return firstChars + truncationString + lastChars
}

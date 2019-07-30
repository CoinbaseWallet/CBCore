package com.coinbase.wallet.core.extensions

import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

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
val String?.asURL: URL? get() = if (this != null) URL(this) else null

/**
 * Hash the string using sha256
 *
 * @throws `NoSuchAlgorithmException` when unable to sha256
 */
@Throws(NoSuchAlgorithmException::class)
fun String.sha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    md.update(this.toByteArray())
    return md.digest().toHexString()
}

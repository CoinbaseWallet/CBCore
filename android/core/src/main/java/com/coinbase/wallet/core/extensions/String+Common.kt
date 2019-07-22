package com.coinbase.wallet.core.extensions

import java.net.URL

/**
 * Determine whether string is a valid hex string
 */
val String.isHexString: Boolean
    get() = try {
        Regex("^[a-f0-9]*$", RegexOption.IGNORE_CASE).matches(this)
    } catch (e: Exception) {
        false
    }

/**
 * Convert optional string to URL if possible
 */
val String?.asURL: URL? get() = if (this != null) URL(this) else null

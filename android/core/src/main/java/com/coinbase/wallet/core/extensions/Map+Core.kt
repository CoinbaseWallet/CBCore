package com.coinbase.wallet.core.extensions

/**
 * Get a non-null value from the map or throw an exception if it's not found
 *
 * @param key a key of type K to get the value for
 *
 * @return the non-null value of type V
 */
fun <K, V> Map<K, V>.require(key: K): V = get(key) ?: throw NoSuchElementException("$key in map is missing.")

/**
 * Make sure map keys and values are all the correct type
 */
inline fun <reified K, reified V> Map<*, *>?.asMapOfType(): Map<K, V>? {
    if (this == null) return null

    return if (all { (key, value) -> key is K && value is V }) {
        @Suppress("UNCHECKED_CAST")
        this as Map<K, V>
    } else {
        null
    }
}

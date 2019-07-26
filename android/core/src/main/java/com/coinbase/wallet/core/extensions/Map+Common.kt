package com.coinbase.wallet.core.extensions

/**
 * Get a non-null value from the map or throw an exception if it's not found
 *
 * @param key a key of type K to get the value for
 *
 * @return the non-null value of type V
 */
fun <K, V> Map<K, V>.require(key: K): V {
    if (!containsKey(key)) {
        throw NoSuchElementException("$key does not exist in map")
    }
    return get(key) ?: throw NoSuchElementException("$key in map is null.")
}

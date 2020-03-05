package com.coinbase.wallet.core.util

class BoundedCache<T, R>(private val maxSize: Int) {
    private var map = LinkedHashMap<T, R>()

    /**
     * Number of entries in the map
     */
    fun count(): Int = map.count()

    /**
     * Add the item to the map
     *
     * @param item the key to add
     */
    operator fun get(item: T): R? = map[item]

    /**
     * Add the item to the map
     *
     * @param item the item to add
     * @param value the value to add
     */
    operator fun set(item: T, value: R?) {
        if (value == null) {
            map.remove(item)
            return
        }

        map[item] = value

        if (map.size > maxSize) {
            map.remove(map.keys.first())
        }
    }
}

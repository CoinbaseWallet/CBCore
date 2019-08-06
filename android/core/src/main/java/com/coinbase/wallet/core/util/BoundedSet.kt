package com.coinbase.wallet.core.util

class BoundedSet<T>(private val maxSize: Int) {
    private var set = LinkedHashSet<T>()

    /**
     * Number of entries in the set
     */
    fun count(): Int = set.count()

    /**
     * Get whether the entry exists
     *
     * @param item The item to check
     *
     * @return True if the set contains the item
     */
    fun has(item: T): Boolean {
        return set.contains(item)
    }

    /**
     * Add the item to the set
     *
     * @param item The item to add
     */
    fun add(item: T) {
        if (has(item)) {
            set.remove(item)
        }

        set.add(item)

        while (set.count() > maxSize && set.isNotEmpty()) {
            set.remove(set.elementAt(0))
        }
    }
}

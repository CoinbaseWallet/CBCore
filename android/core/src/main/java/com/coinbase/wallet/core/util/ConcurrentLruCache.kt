package com.coinbase.wallet.core.util

import android.util.LruCache
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

// / A simple thread safe LRU cache
class ConcurrentLruCache<K, V>(val maxSize: Int) {
    private val cache = LruCache<K, V>(maxSize)
    private val accessLock = ReentrantReadWriteLock()

    /**
     * Subscript getter
     */
    operator fun get(key: K) = accessLock.read { cache.get(key) }

    /**
     * Subscript setter
     */
    operator fun set(key: K, value: V) = accessLock.write { cache.put(key, value) }

    /**
     * Remove key from cache
     */
    fun remove(key: K) {
        accessLock.write { cache.remove(key) }
    }
}

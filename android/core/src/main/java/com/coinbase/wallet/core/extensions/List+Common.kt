package com.coinbase.wallet.core.extensions

import io.reactivex.Single
import io.reactivex.rxkotlin.Singles

/**
 * Convert a list to a map using provided closure
 *
 * @param map initial hashmap
 * @param closure Closure used to populate the map
 *
 * @return final map
 */
inline fun <reified K, reified V, reified E> List<E>.reduceIntoMap(
    map: MutableMap<K, V> = mutableMapOf(),
    closure: (MutableMap<K, V>, E) -> Unit
): Map<K, V> {
    forEach { closure(map, it) }

    return map
}

inline fun <reified T> List<Single<T>>.zipOrEmpty(): Single<List<T>> = Singles.zipOrEmpty(this)
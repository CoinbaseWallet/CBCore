package com.coinbase.wallet.core.util

/**
 * A value wrapper for nullable types
 *
 * @param T The type of the nullable value
 *
 * @property value The value to become optional
 */
data class Optional<out T : Any>(val value: T?) {
    /**
     * Return the internal nullable value
     *
     * @return The nullable value
     */
    fun toNullable(): T? = value
}
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

/**
 * Convert a value to an optional
 *
 * @param T The type of the nullable value
 *
 * @return The value to become optional
 */
fun <T : Any> T?.toOptional(): Optional<T> = Optional(this)

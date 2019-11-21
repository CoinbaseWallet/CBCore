package com.coinbase.wallet.core.models

import java.io.Serializable

data class Seven<out A, out B, out C, out D, out E, out F, out G>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
}

fun <T> Seven<T, T, T, T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth, fifth, sixth, seventh)

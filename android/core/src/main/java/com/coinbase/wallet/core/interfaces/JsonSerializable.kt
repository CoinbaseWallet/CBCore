package com.coinbase.wallet.core.interfaces

/**
 * Conformers to this interface can serialize to a JSON string
 */
interface JsonSerializable {
    fun asJsonString(): String
}

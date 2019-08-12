package com.coinbase.wallet.core.extensions

import org.junit.Assert
import org.junit.Test

class ByteArrayTests {
    @Test
    fun testValidDecodedByteArray() {
        val result = "0x48656c6c6f20576f726c64"

        Assert.assertEquals(result.asHexEncodedData()?.asUTF8String, "Hello World")
    }

    @Test
    fun testInvalidDecodedByteArray() {
        val result = "0xabcdefabcdefabcdef0123456789"

        Assert.assertNull(result.asHexEncodedData()?.asUTF8String)
    }
}

package com.coinbase.wallet.core.util

import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coinbase.wallet.core.extensions.require
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ParcelableMapTests {
    @Test
    fun readAndWriteStringMap() {
        val p = ParcelableMap(mapOf("a" to "b"))
        Bundle()
            .apply {
                putParcelable("foo", p)
            }
            .apply {
                val result = getParcelable<ParcelableMap>("foo")
                assertEquals("b", result.map.require("a") as String)
            }
    }
}

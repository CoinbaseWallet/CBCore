package com.coinbase.wallet.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

class BoundedSetTests {
    @Test
    fun testBoundedSetMaxSize() {
        val set = BoundedSet<String>(2)

        set.add("hish")
        set.add("johnny")
        set.add("andrew")

        assertEquals(2, set.count())
        assertTrue(set.has("johnny"))
        assertTrue(set.has("andrew"))
        assertFalse(set.has("hish"))
    }

    @Test
    fun testBoundedItemsTouched() {
        val set = BoundedSet<String>(2)

        set.add("hish")
        set.add("johnny")
        set.add("hish")
        set.add("andrew")

        assertEquals(2, set.count())
        assertTrue(set.has("andrew"))
        assertFalse(set.has("johnny"))
        assertTrue(set.has("hish"))
    }
}

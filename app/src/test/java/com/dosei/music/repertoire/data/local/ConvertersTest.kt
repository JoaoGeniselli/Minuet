package com.dosei.music.repertoire.data.local

import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun `empty list round-trips`() {
        val encoded = converters.fromStringList(emptyList())
        assertEquals(emptyList<String>(), converters.toStringList(encoded))
    }

    @Test
    fun `preserves order`() {
        val original = listOf("id-3", "id-1", "id-2")
        val restored = converters.toStringList(converters.fromStringList(original))
        assertEquals(original, restored)
    }

    @Test
    fun `handles values containing commas`() {
        val original = listOf("rock, blues", "mpb")
        val restored = converters.toStringList(converters.fromStringList(original))
        assertEquals(original, restored)
    }

    @Test
    fun `empty string decodes to empty list`() {
        assertEquals(emptyList<String>(), converters.toStringList(""))
    }
}

package com.dosei.music.repertoire.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dosei.music.repertoire.data.local.MinuetDatabase
import com.dosei.music.repertoire.data.local.entity.SongEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SongDaoTest {

    private lateinit var database: MinuetDatabase
    private lateinit var dao: SongDao

    private val song = SongEntity(
        id = "1",
        createdAt = 100L,
        title = "Wonderwall",
        artist = "Oasis",
        tags = listOf("rock, brit", "90s"),
        key = "F#m",
        tonalAdjustment = 3,
        bpm = 87,
        notes = null,
        content = "[Em7]Today",
    )

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MinuetDatabase::class.java,
        ).build()
        dao = database.songDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun upsertAndGetById_roundTripsListsAndFields() = runTest {
        dao.upsert(song)

        val loaded = dao.getById("1").first()

        assertEquals(song, loaded)
        // Tags with a comma survive the JSON converter.
        assertEquals(listOf("rock, brit", "90s"), loaded?.tags)
    }

    @Test
    fun upsert_replacesExisting() = runTest {
        dao.upsert(song)
        dao.upsert(song.copy(title = "Don't Look Back in Anger"))

        val loaded = dao.getById("1").first()

        assertEquals("Don't Look Back in Anger", loaded?.title)
    }

    @Test
    fun getAll_ordersByCreatedAtDescending() = runTest {
        dao.upsert(song.copy(id = "a", createdAt = 1L))
        dao.upsert(song.copy(id = "b", createdAt = 3L))
        dao.upsert(song.copy(id = "c", createdAt = 2L))

        val ids = dao.getAll().first().map { it.id }

        assertEquals(listOf("b", "c", "a"), ids)
    }

    @Test
    fun deleteById_removesRow() = runTest {
        dao.upsert(song)
        dao.deleteById("1")

        assertNull(dao.getById("1").first())
    }
}

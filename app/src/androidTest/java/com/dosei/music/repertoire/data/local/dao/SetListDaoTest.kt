package com.dosei.music.repertoire.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dosei.music.repertoire.data.local.MinuetDatabase
import com.dosei.music.repertoire.data.local.entity.SetListEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SetListDaoTest {

    private lateinit var database: MinuetDatabase
    private lateinit var dao: SetListDao

    private val setList = SetListEntity(
        id = "1",
        title = "Sunday Service",
        tags = listOf("gospel"),
        createdAt = 100L,
        orderedSongIds = listOf("song-3", "song-1", "song-2"),
    )

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MinuetDatabase::class.java,
        ).build()
        dao = database.setListDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun upsertAndGetById_preservesSongOrder() = runTest {
        dao.upsert(setList)

        val loaded = dao.getById("1").first()

        assertEquals(setList, loaded)
        // Ordering of the setlist must be preserved exactly.
        assertEquals(listOf("song-3", "song-1", "song-2"), loaded?.orderedSongIds)
    }

    @Test
    fun deleteById_removesRow() = runTest {
        dao.upsert(setList)
        dao.deleteById("1")

        assertNull(dao.getById("1").first())
    }
}

package com.dosei.music.repertoire.data.repository

import app.cash.turbine.test
import com.dosei.music.repertoire.data.local.dao.SongDao
import com.dosei.music.repertoire.data.local.entity.SongEntity
import com.dosei.music.repertoire.domain.model.Song
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SongRepositoryImplTest {

    private val dao: SongDao = mockk(relaxed = true)
    private val repository = SongRepositoryImpl(dao)

    private val entity = SongEntity(
        id = "1",
        createdAt = 100L,
        title = "Wonderwall",
        artist = "Oasis",
        tags = listOf("rock"),
        key = "F#m",
        tonalAdjustment = 0,
        bpm = 87,
        notes = null,
        content = "[Em7]Today is gonna be the day",
    )

    @Test
    fun `getSongs maps entities to domain`() = runTest {
        every { dao.getAll() } returns flowOf(listOf(entity))

        repository.getSongs().test {
            val songs = awaitItem()
            assertEquals(1, songs.size)
            assertEquals("Wonderwall", songs.first().title)
            assertEquals(listOf("rock"), songs.first().tags)
            awaitComplete()
        }
    }

    @Test
    fun `getSong maps null entity to null`() = runTest {
        every { dao.getById("1") } returns flowOf(null)

        repository.getSong("1").test {
            assertEquals(null, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `saveSong upserts mapped entity`() = runTest {
        val song = Song(
            id = "2",
            createdAt = 200L,
            title = "Creep",
            artist = "Radiohead",
            tags = emptyList(),
            key = "G",
            tonalAdjustment = 2,
            bpm = 92,
            notes = "capo 1",
            content = "[G]When you were here before",
        )
        coEvery { dao.upsert(any()) } returns Unit

        repository.saveSong(song)

        coVerify { dao.upsert(match { it.id == "2" && it.title == "Creep" }) }
    }

    @Test
    fun `deleteSong delegates to dao`() = runTest {
        repository.deleteSong("1")
        coVerify { dao.deleteById("1") }
    }
}

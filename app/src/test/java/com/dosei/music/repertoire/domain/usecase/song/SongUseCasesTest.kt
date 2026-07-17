package com.dosei.music.repertoire.domain.usecase.song

import app.cash.turbine.test
import com.dosei.music.repertoire.domain.model.Song
import com.dosei.music.repertoire.domain.repository.SongRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SongUseCasesTest {

    private val repository: SongRepository = mockk(relaxed = true)

    private val song = Song(
        id = "1",
        createdAt = 1L,
        title = "Song",
        artist = "Artist",
        tags = emptyList(),
        key = null,
        tonalAdjustment = 0,
        bpm = null,
        notes = null,
        content = "",
    )

    @Test
    fun `GetSongsUseCase returns repository flow`() = runTest {
        every { repository.getSongs() } returns flowOf(listOf(song))

        GetSongsUseCase(repository).invoke().test {
            assertEquals(listOf(song), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `SaveSongUseCase delegates to repository`() = runTest {
        SaveSongUseCase(repository).invoke(song)
        coVerify { repository.saveSong(song) }
    }

    @Test
    fun `DeleteSongUseCase delegates to repository`() = runTest {
        DeleteSongUseCase(repository).invoke("1")
        coVerify { repository.deleteSong("1") }
    }
}

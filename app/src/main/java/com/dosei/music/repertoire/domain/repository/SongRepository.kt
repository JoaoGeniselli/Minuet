package com.dosei.music.repertoire.domain.repository

import com.dosei.music.repertoire.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    fun getSongs(): Flow<List<Song>>
    fun getSong(id: String): Flow<Song?>
    suspend fun saveSong(song: Song)
    suspend fun deleteSong(id: String)
}

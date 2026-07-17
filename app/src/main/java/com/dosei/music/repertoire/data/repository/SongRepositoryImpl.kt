package com.dosei.music.repertoire.data.repository

import com.dosei.music.repertoire.data.local.dao.SongDao
import com.dosei.music.repertoire.data.mapper.toDomain
import com.dosei.music.repertoire.data.mapper.toEntity
import com.dosei.music.repertoire.domain.model.Song
import com.dosei.music.repertoire.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val dao: SongDao,
) : SongRepository {

    override fun getSongs(): Flow<List<Song>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    override fun getSong(id: String): Flow<Song?> =
        dao.getById(id).map { it?.toDomain() }

    override suspend fun saveSong(song: Song) = dao.upsert(song.toEntity())

    override suspend fun deleteSong(id: String) = dao.deleteById(id)
}

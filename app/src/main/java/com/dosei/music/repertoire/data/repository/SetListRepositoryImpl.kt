package com.dosei.music.repertoire.data.repository

import com.dosei.music.repertoire.data.local.dao.SetListDao
import com.dosei.music.repertoire.data.mapper.toDomain
import com.dosei.music.repertoire.data.mapper.toEntity
import com.dosei.music.repertoire.domain.model.SetList
import com.dosei.music.repertoire.domain.repository.SetListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetListRepositoryImpl @Inject constructor(
    private val dao: SetListDao,
) : SetListRepository {

    override fun getSetLists(): Flow<List<SetList>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    override fun getSetList(id: String): Flow<SetList?> =
        dao.getById(id).map { it?.toDomain() }

    override suspend fun saveSetList(setList: SetList) = dao.upsert(setList.toEntity())

    override suspend fun deleteSetList(id: String) = dao.deleteById(id)
}

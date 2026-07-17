package com.dosei.music.repertoire.domain.repository

import com.dosei.music.repertoire.domain.model.SetList
import kotlinx.coroutines.flow.Flow

interface SetListRepository {
    fun getSetLists(): Flow<List<SetList>>
    fun getSetList(id: String): Flow<SetList?>
    suspend fun saveSetList(setList: SetList)
    suspend fun deleteSetList(id: String)
}

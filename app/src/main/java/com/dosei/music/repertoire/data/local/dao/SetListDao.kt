package com.dosei.music.repertoire.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dosei.music.repertoire.data.local.entity.SetListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SetListDao {

    @Query("SELECT * FROM set_lists ORDER BY createdAt DESC")
    fun getAll(): Flow<List<SetListEntity>>

    @Query("SELECT * FROM set_lists WHERE id = :id")
    fun getById(id: String): Flow<SetListEntity?>

    @Upsert
    suspend fun upsert(setList: SetListEntity)

    @Query("DELETE FROM set_lists WHERE id = :id")
    suspend fun deleteById(id: String)
}

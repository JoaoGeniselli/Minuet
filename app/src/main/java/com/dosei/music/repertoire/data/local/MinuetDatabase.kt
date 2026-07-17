package com.dosei.music.repertoire.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dosei.music.repertoire.data.local.dao.SetListDao
import com.dosei.music.repertoire.data.local.dao.SongDao
import com.dosei.music.repertoire.data.local.entity.SetListEntity
import com.dosei.music.repertoire.data.local.entity.SongEntity

@Database(
    entities = [SongEntity::class, SetListEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class MinuetDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun setListDao(): SetListDao

    companion object {
        const val NAME = "minuet.db"
    }
}

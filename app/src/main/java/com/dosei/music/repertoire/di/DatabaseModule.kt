package com.dosei.music.repertoire.di

import android.content.Context
import androidx.room.Room
import com.dosei.music.repertoire.data.local.MinuetDatabase
import com.dosei.music.repertoire.data.local.dao.SetListDao
import com.dosei.music.repertoire.data.local.dao.SongDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MinuetDatabase =
        Room.databaseBuilder(context, MinuetDatabase::class.java, MinuetDatabase.NAME).build()

    @Provides
    fun provideSongDao(database: MinuetDatabase): SongDao = database.songDao()

    @Provides
    fun provideSetListDao(database: MinuetDatabase): SetListDao = database.setListDao()
}

package com.dosei.music.repertoire.di

import com.dosei.music.repertoire.data.repository.SetListRepositoryImpl
import com.dosei.music.repertoire.data.repository.SongRepositoryImpl
import com.dosei.music.repertoire.domain.repository.SetListRepository
import com.dosei.music.repertoire.domain.repository.SongRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSongRepository(impl: SongRepositoryImpl): SongRepository

    @Binds
    @Singleton
    abstract fun bindSetListRepository(impl: SetListRepositoryImpl): SetListRepository
}

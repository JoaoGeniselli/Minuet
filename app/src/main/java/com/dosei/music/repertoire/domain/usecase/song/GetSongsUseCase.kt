package com.dosei.music.repertoire.domain.usecase.song

import com.dosei.music.repertoire.domain.model.Song
import com.dosei.music.repertoire.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(
    private val repository: SongRepository,
) {
    operator fun invoke(): Flow<List<Song>> = repository.getSongs()
}

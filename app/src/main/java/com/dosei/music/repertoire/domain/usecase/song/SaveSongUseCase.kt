package com.dosei.music.repertoire.domain.usecase.song

import com.dosei.music.repertoire.domain.model.Song
import com.dosei.music.repertoire.domain.repository.SongRepository
import javax.inject.Inject

class SaveSongUseCase @Inject constructor(
    private val repository: SongRepository,
) {
    suspend operator fun invoke(song: Song) = repository.saveSong(song)
}

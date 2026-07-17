package com.dosei.music.repertoire.domain.usecase.song

import com.dosei.music.repertoire.domain.repository.SongRepository
import javax.inject.Inject

class DeleteSongUseCase @Inject constructor(
    private val repository: SongRepository,
) {
    suspend operator fun invoke(id: String) = repository.deleteSong(id)
}

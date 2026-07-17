package com.dosei.music.repertoire.domain.usecase.setlist

import com.dosei.music.repertoire.domain.repository.SetListRepository
import javax.inject.Inject

class DeleteSetListUseCase @Inject constructor(
    private val repository: SetListRepository,
) {
    suspend operator fun invoke(id: String) = repository.deleteSetList(id)
}

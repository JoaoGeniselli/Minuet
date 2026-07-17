package com.dosei.music.repertoire.domain.usecase.setlist

import com.dosei.music.repertoire.domain.model.SetList
import com.dosei.music.repertoire.domain.repository.SetListRepository
import javax.inject.Inject

class SaveSetListUseCase @Inject constructor(
    private val repository: SetListRepository,
) {
    suspend operator fun invoke(setList: SetList) = repository.saveSetList(setList)
}

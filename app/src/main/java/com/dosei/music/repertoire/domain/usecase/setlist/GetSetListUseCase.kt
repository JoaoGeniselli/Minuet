package com.dosei.music.repertoire.domain.usecase.setlist

import com.dosei.music.repertoire.domain.model.SetList
import com.dosei.music.repertoire.domain.repository.SetListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSetListUseCase @Inject constructor(
    private val repository: SetListRepository,
) {
    operator fun invoke(id: String): Flow<SetList?> = repository.getSetList(id)
}

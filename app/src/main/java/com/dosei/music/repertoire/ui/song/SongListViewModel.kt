package com.dosei.music.repertoire.ui.song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosei.music.repertoire.domain.model.Song
import com.dosei.music.repertoire.domain.usecase.song.GetSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

sealed interface SongListUiState {
    data object Loading : SongListUiState
    data class Success(val songs: List<Song>) : SongListUiState
    data class Error(val message: String) : SongListUiState
}

sealed interface SongListUiEvent {
    data object Retry : SongListUiEvent
}

@HiltViewModel
class SongListViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SongListUiState>(SongListUiState.Loading)
    val uiState: StateFlow<SongListUiState> = _uiState.asStateFlow()

    init {
        loadSongs()
    }

    fun onEvent(event: SongListUiEvent) {
        when (event) {
            SongListUiEvent.Retry -> loadSongs()
        }
    }

    private fun loadSongs() {
        _uiState.value = SongListUiState.Loading
        getSongsUseCase()
            .onEach { songs -> _uiState.value = SongListUiState.Success(songs) }
            .catch { throwable -> _uiState.value = SongListUiState.Error(throwable.message.orEmpty()) }
            .launchIn(viewModelScope)
    }
}

package com.dosei.music.repertoire.ui.song

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SongListScreen(
    onSongClick: (String) -> Unit,
    onAddSongClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SongListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SongListContent(
        uiState = uiState,
        onSongClick = onSongClick,
        onAddSongClick = onAddSongClick,
        onRetry = { viewModel.onEvent(SongListUiEvent.Retry) },
        modifier = modifier,
    )
}

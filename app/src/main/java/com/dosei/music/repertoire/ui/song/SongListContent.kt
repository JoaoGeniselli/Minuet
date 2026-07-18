package com.dosei.music.repertoire.ui.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dosei.music.repertoire.R
import com.dosei.music.repertoire.domain.model.Song
import com.dosei.music.repertoire.ui.theme.MinuetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListContent(
    uiState: SongListUiState,
    onSongClick: (String) -> Unit,
    onAddSongClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.str_song_list_title)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddSongClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.str_song_list_add_action),
                )
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is SongListUiState.Loading -> LoadingState(innerPadding)
            is SongListUiState.Error -> ErrorState(innerPadding, onRetry)
            is SongListUiState.Success -> if (uiState.songs.isEmpty()) {
                EmptyState(innerPadding)
            } else {
                SongList(innerPadding, uiState.songs, onSongClick)
            }
        }
    }
}

@Composable
private fun LoadingState(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Default.MusicNote,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.str_song_list_empty_title),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.str_song_list_empty_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ErrorState(padding: PaddingValues, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.str_song_list_error_message),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.str_song_list_error_retry))
        }
    }
}

@Composable
private fun SongList(padding: PaddingValues, songs: List<Song>, onSongClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = padding,
    ) {
        items(songs, key = { it.id }) { song ->
            SongListItem(song = song, onClick = { onSongClick(song.id) })
        }
    }
}

@Composable
fun SongListItem(song: Song, onClick: () -> Unit, modifier: Modifier = Modifier) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        headlineContent = { Text(song.title) },
        supportingContent = { Text(song.artist) },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End) {
                song.key?.let { key -> Text(key, style = MaterialTheme.typography.labelLarge) }
                song.bpm?.let { bpm ->
                    Text(
                        text = stringResource(R.string.str_song_list_item_bpm_format, bpm),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
    )
}

private val sampleSongs = listOf(
    Song(
        id = "1",
        createdAt = 0L,
        title = "Águas de Março",
        artist = "Elis Regina",
        tags = listOf("MPB"),
        key = "Bm",
        tonalAdjustment = 0,
        bpm = 120,
        notes = null,
        content = "",
    ),
    Song(
        id = "2",
        createdAt = 0L,
        title = "Garota de Ipanema",
        artist = "Tom Jobim",
        tags = listOf("Bossa Nova"),
        key = null,
        tonalAdjustment = 0,
        bpm = null,
        notes = null,
        content = "",
    ),
)

@Preview(showBackground = true)
@Composable
private fun SongListContentLoadingPreview() {
    MinuetTheme {
        SongListContent(
            uiState = SongListUiState.Loading,
            onSongClick = {},
            onAddSongClick = {},
            onRetry = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SongListContentSuccessPreview() {
    MinuetTheme {
        SongListContent(
            uiState = SongListUiState.Success(sampleSongs),
            onSongClick = {},
            onAddSongClick = {},
            onRetry = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SongListContentEmptyPreview() {
    MinuetTheme {
        SongListContent(
            uiState = SongListUiState.Success(emptyList()),
            onSongClick = {},
            onAddSongClick = {},
            onRetry = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SongListContentErrorPreview() {
    MinuetTheme {
        SongListContent(
            uiState = SongListUiState.Error("network error"),
            onSongClick = {},
            onAddSongClick = {},
            onRetry = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SongListItemPreview() {
    MinuetTheme {
        SongListItem(song = sampleSongs.first(), onClick = {})
    }
}

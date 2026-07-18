package com.dosei.music.repertoire.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.ui.graphics.vector.ImageVector
import com.dosei.music.repertoire.R

enum class TopLevelDestination(
    val route: Routes,
    @StringRes val labelRes: Int,
    val icon: ImageVector,
) {
    SONGS(Routes.Songs, R.string.str_nav_songs, Icons.Default.LibraryMusic),
    SETLISTS(Routes.SetLists, R.string.str_nav_setlists, Icons.AutoMirrored.Filled.QueueMusic),
}

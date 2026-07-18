package com.dosei.music.repertoire.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Songs : Routes

    @Serializable
    data object SetLists : Routes

    @Serializable
    data class SongDetail(val id: String) : Routes

    @Serializable
    data object AddSong : Routes
}

package com.dosei.music.repertoire.domain.model

/**
 * An ordered collection of songs to be performed, referenced by their ids.
 */
data class SetList(
    val id: String,
    val title: String,
    val tags: List<String>,
    val createdAt: Long,
    val orderedSongIds: List<String>,
)

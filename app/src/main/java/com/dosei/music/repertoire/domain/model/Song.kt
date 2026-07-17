package com.dosei.music.repertoire.domain.model

/**
 * A song in the repertoire, including its ChordPro content and tonal metadata.
 */
data class Song(
    val id: String,
    val createdAt: Long,
    val title: String,
    val artist: String,
    val tags: List<String>,
    val key: String?,
    val tonalAdjustment: Int,
    val bpm: Int?,
    val notes: String?,
    val content: String,
)

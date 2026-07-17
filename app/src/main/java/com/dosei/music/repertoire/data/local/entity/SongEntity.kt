package com.dosei.music.repertoire.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey val id: String,
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

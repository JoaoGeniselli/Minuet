package com.dosei.music.repertoire.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "set_lists")
data class SetListEntity(
    @PrimaryKey val id: String,
    val title: String,
    val tags: List<String>,
    val createdAt: Long,
    val orderedSongIds: List<String>,
)

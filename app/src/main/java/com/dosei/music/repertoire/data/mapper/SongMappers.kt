package com.dosei.music.repertoire.data.mapper

import com.dosei.music.repertoire.data.local.entity.SongEntity
import com.dosei.music.repertoire.domain.model.Song

fun SongEntity.toDomain(): Song = Song(
    id = id,
    createdAt = createdAt,
    title = title,
    artist = artist,
    tags = tags,
    key = key,
    tonalAdjustment = tonalAdjustment,
    bpm = bpm,
    notes = notes,
    content = content,
)

fun Song.toEntity(): SongEntity = SongEntity(
    id = id,
    createdAt = createdAt,
    title = title,
    artist = artist,
    tags = tags,
    key = key,
    tonalAdjustment = tonalAdjustment,
    bpm = bpm,
    notes = notes,
    content = content,
)

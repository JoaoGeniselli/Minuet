package com.dosei.music.repertoire.data.mapper

import com.dosei.music.repertoire.data.local.entity.SetListEntity
import com.dosei.music.repertoire.domain.model.SetList

fun SetListEntity.toDomain(): SetList = SetList(
    id = id,
    title = title,
    tags = tags,
    createdAt = createdAt,
    orderedSongIds = orderedSongIds,
)

fun SetList.toEntity(): SetListEntity = SetListEntity(
    id = id,
    title = title,
    tags = tags,
    createdAt = createdAt,
    orderedSongIds = orderedSongIds,
)

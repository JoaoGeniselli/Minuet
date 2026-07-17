package com.dosei.music.repertoire.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

/**
 * Room type converters for the [List]<[String]> columns (`tags`, `orderedSongIds`).
 *
 * JSON encoding is used instead of a plain delimiter so that free-text tags containing the
 * separator character are stored safely, while the ordering of the list is preserved.
 */
class Converters {

    @TypeConverter
    fun fromStringList(value: List<String>): String =
        Json.encodeToString(stringListSerializer, value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        if (value.isEmpty()) emptyList() else Json.decodeFromString(stringListSerializer, value)

    private companion object {
        val stringListSerializer = ListSerializer(String.serializer())
    }
}

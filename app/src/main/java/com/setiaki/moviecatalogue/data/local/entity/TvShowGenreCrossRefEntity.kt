package com.setiaki.moviecatalogue.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "tv_show_genre_cross_ref", primaryKeys = ["tv_show_id", "genre_id"])
data class TvShowGenreCrossRefEntity(
    @ColumnInfo(name = "tv_show_id", index = true)
    @NonNull
    val tvShowId: Int,

    @ColumnInfo(name = "genre_id", index = true)
    @NonNull
    val genreId: Int
)

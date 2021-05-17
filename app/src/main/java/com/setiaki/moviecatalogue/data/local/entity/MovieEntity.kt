package com.setiaki.moviecatalogue.data.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    val movieId: Int,

    @ColumnInfo(name = "title")
    @NonNull
    val title: String,

    @ColumnInfo(name = "poster_path")
    @Nullable
    val posterPath: String?,

    @ColumnInfo(name = "overview")
    @NonNull
    val overview: String,

    @ColumnInfo(name = "release_date")
    @NonNull
    val releaseDate: String,

    @ColumnInfo(name = "vote_average")
    @NonNull
    val voteAverage: Float,

    @ColumnInfo(name = "is_favorited", defaultValue = "0")
    @NonNull
    var isFavorited: Boolean
)

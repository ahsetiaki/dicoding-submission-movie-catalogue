package com.setiaki.moviecatalogue.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class MovieWithGenre(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "genre_id",
        associateBy = Junction(MovieGenreCrossRefEntity::class)
    )
    val genres: List<GenreEntity>
)

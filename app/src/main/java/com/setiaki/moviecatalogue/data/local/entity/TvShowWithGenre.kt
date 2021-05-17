package com.setiaki.moviecatalogue.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class TvShowWithGenre(
    @Embedded val tvShow: TvShowEntity,
    @Relation(
        parentColumn = "tv_show_id",
        entityColumn = "genre_id",
        associateBy = Junction(TvShowGenreCrossRefEntity::class)
    )
    val genres: List<GenreEntity>
)

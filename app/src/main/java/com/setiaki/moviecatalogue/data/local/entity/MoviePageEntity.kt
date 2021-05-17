package com.setiaki.moviecatalogue.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_pages")
data class MoviePageEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val id: Int,

    @ColumnInfo(name = "prev_page")
    val prevPage: Int?,

    @ColumnInfo(name = "next_page")
    val nextPage: Int?
)

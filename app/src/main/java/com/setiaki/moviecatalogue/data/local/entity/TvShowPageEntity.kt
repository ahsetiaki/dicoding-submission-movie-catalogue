package com.setiaki.moviecatalogue.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tv_show_pages")
data class TvShowPageEntity(
    @PrimaryKey
    @ColumnInfo(name = "tv_show_id")
    val id: Int,

    @ColumnInfo(name = "prev_page")
    val prevPage: Int?,

    @ColumnInfo(name = "next_page")
    val nextPage: Int?
)

package com.setiaki.moviecatalogue.data.remote.response

import com.google.gson.annotations.SerializedName


data class MovieDetailResponse(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("title")
    val title: String = "",

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("overview")
    val overview: String = "",

    @field:SerializedName("genres")
    val genres: List<Genre> = arrayListOf<Genre>(),

    @field:SerializedName("release_date")
    val releaseDate: String = "",

    @field:SerializedName("vote_average")
    val voteAverage: Number = 0
)

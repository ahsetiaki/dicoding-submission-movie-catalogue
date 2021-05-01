package com.setiaki.moviecatalogue.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieTopRatedResponse(
    @field:SerializedName("results")
    val results: List<MovieDetailResponse> = arrayListOf<MovieDetailResponse>(),

    @field:SerializedName("total_results")
    val totalResults: Int = 0
)

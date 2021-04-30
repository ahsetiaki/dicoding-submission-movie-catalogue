package com.setiaki.moviecatalogue.response

import com.google.gson.annotations.SerializedName

data class TvShowTopRatedResponse(
    @field:SerializedName("results")
    val results: List<TvShowDetailResponse> = arrayListOf<TvShowDetailResponse>(),

    @field:SerializedName("total_results")
    val totalResults: Int = 0
)

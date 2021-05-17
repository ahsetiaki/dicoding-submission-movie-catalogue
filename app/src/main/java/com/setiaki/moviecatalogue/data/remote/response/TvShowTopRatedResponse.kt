package com.setiaki.moviecatalogue.data.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowTopRatedResponse(
    @field:SerializedName("results")
    val results: List<TvShowResponse> = arrayListOf<TvShowResponse>()
)

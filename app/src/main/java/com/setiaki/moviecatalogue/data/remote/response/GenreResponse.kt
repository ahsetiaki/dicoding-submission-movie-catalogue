package com.setiaki.moviecatalogue.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("name")
    val name: String = ""
)

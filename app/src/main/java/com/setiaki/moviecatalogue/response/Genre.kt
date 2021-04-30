package com.setiaki.moviecatalogue.response

import com.google.gson.annotations.SerializedName

data class Genre(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("name")
    val name: String = ""
)

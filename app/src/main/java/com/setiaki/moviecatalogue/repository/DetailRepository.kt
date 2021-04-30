package com.setiaki.moviecatalogue.repository

import com.setiaki.moviecatalogue.api.TMDBWebservice
import com.setiaki.moviecatalogue.response.MovieDetailResponse
import com.setiaki.moviecatalogue.response.TvShowDetailResponse

class DetailRepository {
    private val webservice: TMDBWebservice = TMDBWebservice.create()

    suspend fun getMovieDetail(id: Int): MovieDetailResponse {
        return webservice.getMovieDetail(id)
    }

    suspend fun getTVShowDetail(id: Int): TvShowDetailResponse {
        return webservice.getTvShowDetail(id)
    }
}
package com.setiaki.moviecatalogue.data.repository

import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.data.remote.response.MovieDetailResponse
import com.setiaki.moviecatalogue.data.remote.response.TvShowDetailResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DetailRepository @Inject constructor(private val webservice: TMDBWebservice) {
    suspend fun getMovieDetail(id: Int): MovieDetailResponse {
        return webservice.getMovieDetail(id)
    }

    suspend fun getTVShowDetail(id: Int): TvShowDetailResponse {
        return webservice.getTvShowDetail(id)
    }
}
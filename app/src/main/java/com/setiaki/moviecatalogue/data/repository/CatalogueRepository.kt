package com.setiaki.moviecatalogue.data.repository

import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.data.remote.response.MovieDetailResponse
import com.setiaki.moviecatalogue.data.remote.response.TvShowDetailResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CatalogueRepository @Inject constructor(private val webservice: TMDBWebservice) {
    suspend fun getTopRatedMovies(): List<MovieDetailResponse> {
        return webservice.getTopRatedMovies().results
    }

    suspend fun getTopRatedTVShows(): List<TvShowDetailResponse> {
        return webservice.getTopRatedTvShows().results
    }
}
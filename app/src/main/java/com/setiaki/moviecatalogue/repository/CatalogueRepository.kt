package com.setiaki.moviecatalogue.repository

import com.setiaki.moviecatalogue.api.TMDBWebservice
import com.setiaki.moviecatalogue.response.MovieTopRatedResponse
import com.setiaki.moviecatalogue.response.TvShowTopRatedResponse

class CatalogueRepository {
    private val webservice: TMDBWebservice = TMDBWebservice.create()

    suspend fun getTopRatedMovies(): MovieTopRatedResponse {
        return webservice.getTopRatedMovies()
    }

    suspend fun getTopRatedTVShows(): TvShowTopRatedResponse {
        return webservice.getTopRatedTvShows()
    }

}
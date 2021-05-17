package com.setiaki.moviecatalogue.data.remote.api

import com.setiaki.moviecatalogue.BuildConfig
import com.setiaki.moviecatalogue.data.remote.response.MovieResponse
import com.setiaki.moviecatalogue.data.remote.response.MovieTopRatedResponse
import com.setiaki.moviecatalogue.data.remote.response.TvShowResponse
import com.setiaki.moviecatalogue.data.remote.response.TvShowTopRatedResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBWebservice {
    @GET("{tmdb_version}/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Path("tmdb_version") tmdbVersion: Int = TMDB_VERSION,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int
    ): MovieTopRatedResponse

    @GET("{tmdb_version}/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Path("tmdb_version") tmdbVersion: Int = TMDB_VERSION,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieResponse

    @GET("{tmdb_version}/tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Path("tmdb_version") tmdbVersion: Int = TMDB_VERSION,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int
    ): TvShowTopRatedResponse

    @GET("{tmbd_version}/tv/{tv_id}")
    suspend fun getTvShowDetail(
        @Path("tv_id") id: Int,
        @Path("tmbd_version") tmdbVersion: Int = TMDB_VERSION,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): TvShowResponse

    companion object {
        private const val TMDB_VERSION = 3
        private const val BASE_URL = "https://api.themoviedb.org/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/original/"
        const val TMDB_STARTING_PAGE_INDEX = 1
        const val NETWORK_SIZE = 20

        fun create(): TMDBWebservice {
//            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }
//
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logger)
//                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TMDBWebservice::class.java)
        }
    }
}
package com.setiaki.moviecatalogue.data.repository

import androidx.room.withTransaction
import com.setiaki.moviecatalogue.data.local.database.AppDatabase
import com.setiaki.moviecatalogue.data.local.entity.MovieWithGenre
import com.setiaki.moviecatalogue.data.local.entity.TvShowWithGenre
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.data.util.mapper.genrecrossref.MovieGenreCrossRefMapper
import com.setiaki.moviecatalogue.data.util.mapper.genrecrossref.TvShowGenreCrossRefMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.GenreMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.MovieMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.TvShowMapper
import com.setiaki.moviecatalogue.data.util.networkbound.Resource
import com.setiaki.moviecatalogue.data.util.networkbound.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DetailRepository
@Inject constructor(
    private val webservice: TMDBWebservice,
    private val database: AppDatabase,
    private val movieMapper: MovieMapper,
    private val tvShowMapper: TvShowMapper,
    private val genreMapper: GenreMapper,
    private val movieGenreCrossRefMapper: MovieGenreCrossRefMapper,
    private val tvShowGenreCrossRefMapper: TvShowGenreCrossRefMapper
) {
    fun getMovieDetail(id: Int): Flow<Resource<MovieWithGenre>> {
        return networkBoundResource(
            query = { database.movieGenreCrossRefDao().getMovieWithGenre(id) },
            fetch = { webservice.getMovieDetail(id) },
            saveFetchResult = {
                val movieResponse = it
                val genreResponses = movieResponse.genres

                val movieEntity = movieMapper.mapResponseToEntity(movieResponse)
                val genreEntities = genreMapper.mapResponseListToEntityList(genreResponses)
                val movieGenreCrossRefEntities =
                    movieGenreCrossRefMapper.mapResponseToCrossRefs(movieResponse)

                with(database) {
                    withTransaction {
                        movieDao().insertMovie(movieEntity)
                        genreDao().insertGenres(genreEntities)
                        movieGenreCrossRefDao().insertMovieGenreCrossRefs(movieGenreCrossRefEntities)
                    }
                }
            },
            shouldFetch = { it.genres.isNullOrEmpty() }
        )
    }

    fun getTVShowDetail(id: Int): Flow<Resource<TvShowWithGenre>> {
        return networkBoundResource(
            query = { database.tvShowGenreCrossRefDao().getTvShowWithGenre(id) },
            fetch = { webservice.getTvShowDetail(id) },
            saveFetchResult = {
                val tvShowResponse = it
                val genreResponses = tvShowResponse.genres

                val tvShowEntity = tvShowMapper.mapResponseToEntity(tvShowResponse)
                val genreEntities = genreMapper.mapResponseListToEntityList(genreResponses)
                val tvShowGenreCrossRefEntities =
                    tvShowGenreCrossRefMapper.mapResponseToCrossRefs(tvShowResponse)

                with(database) {
                    withTransaction {
                        tvShowDao().insertTvShow(tvShowEntity)
                        genreDao().insertGenres(genreEntities)
                        tvShowGenreCrossRefDao().insertTvShowGenreCrossRefs(
                            tvShowGenreCrossRefEntities
                        )
                    }
                }
            },
            shouldFetch = { it.genres.isNullOrEmpty() }
        )
    }


    suspend fun toggleMovieDetailFavoriteStatus(id: Int) {
        with(database) {
            val toggledEntity = movieDao().getMovie(id).apply {
                this.isFavorited = !this.isFavorited
            }
            movieDao().updateMovie(toggledEntity)
        }
    }

    suspend fun toggleTvShowDetailFavoriteStatus(id: Int) {
        with(database) {

            val toggledEntity = tvShowDao().getTvShow(id).apply {
                this.isFavorited = !this.isFavorited
            }
            tvShowDao().updateTvShow(toggledEntity)
        }
    }
}
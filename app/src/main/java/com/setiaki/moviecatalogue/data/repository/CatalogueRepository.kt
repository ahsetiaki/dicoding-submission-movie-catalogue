package com.setiaki.moviecatalogue.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.setiaki.moviecatalogue.data.local.database.AppDatabase
import com.setiaki.moviecatalogue.data.local.entity.MovieEntity
import com.setiaki.moviecatalogue.data.local.entity.TvShowEntity
import com.setiaki.moviecatalogue.data.paging.MovieRemoteMediator
import com.setiaki.moviecatalogue.data.paging.TvShowRemoteMediator
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice.Companion.NETWORK_SIZE
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.MovieMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.TvShowMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CatalogueRepository
@Inject constructor(
    private val webservice: TMDBWebservice,
    private val database: AppDatabase,
    private val movieMapper: MovieMapper,
    private val tvShowMapper: TvShowMapper
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getTopRatedMovies(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_SIZE, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(webservice, database, movieMapper),
            pagingSourceFactory = { database.movieDao().getTopRatedMovies() }
        ).flow

    }

    @OptIn(ExperimentalPagingApi::class)
    fun getTopRatedTVShows(): Flow<PagingData<TvShowEntity>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_SIZE, enablePlaceholders = false),
            remoteMediator = TvShowRemoteMediator(webservice, database, tvShowMapper),
            pagingSourceFactory = { database.tvShowDao().getTopRatedTvShows() }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getFavoritedMovies(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 4, enablePlaceholders = false),
            pagingSourceFactory = { database.movieDao().getFavoritedMovies() }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getFavoritedTvShows(): Flow<PagingData<TvShowEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 4, enablePlaceholders = false),
            pagingSourceFactory = { database.tvShowDao().getFavoritedTvShow() }
        ).flow
    }
}
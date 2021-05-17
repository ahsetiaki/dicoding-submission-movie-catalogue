package com.setiaki.moviecatalogue.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.setiaki.moviecatalogue.data.local.database.AppDatabase
import com.setiaki.moviecatalogue.data.local.entity.MovieEntity
import com.setiaki.moviecatalogue.data.local.entity.MoviePageEntity
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice.Companion.TMDB_STARTING_PAGE_INDEX
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.MovieMapper
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val webservice: TMDBWebservice,
    private val database: AppDatabase,
    private val movieMapper: MovieMapper
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remotePage = getClosestPageToCurrentPosition(state)
                remotePage?.nextPage?.minus(1) ?: TMDB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remotePage = getPageForFirstItem(state)
                val prevPage = remotePage?.prevPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remotePage != null)
                prevPage

            }
            LoadType.APPEND -> {
                val remotePage = getPageForLastItem(state)
                val nextPage = remotePage?.nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remotePage != null)
                nextPage

            }
        }

        try {
            val apiResponse = webservice.getTopRatedMovies(page = page)
            val movieResponse = apiResponse.results
            val movieEntities = movieMapper.mapResponseListToEntityList(movieResponse)

            val endOfPaginationReached = movieResponse.isEmpty()
            val prevPage = if (page == TMDB_STARTING_PAGE_INDEX) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1
            val pageEntities = movieResponse.map {
                MoviePageEntity(it.id, prevPage, nextPage)
            }

            with(database) {
                withTransaction {
                    moviePageDao().insertAll(pageEntities)
                    movieDao().insertMovies(movieEntities)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }


    }

    private suspend fun getPageForLastItem(state: PagingState<Int, MovieEntity>): MoviePageEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                database.moviePageDao().getMoviePageByMovieId(movie.movieId)
            }
    }

    private suspend fun getPageForFirstItem(state: PagingState<Int, MovieEntity>): MoviePageEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                database.moviePageDao().getMoviePageByMovieId(movie.movieId)
            }
    }

    private suspend fun getClosestPageToCurrentPosition(state: PagingState<Int, MovieEntity>): MoviePageEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { movieId ->
                database.moviePageDao().getMoviePageByMovieId(movieId)
            }
        }
    }


}
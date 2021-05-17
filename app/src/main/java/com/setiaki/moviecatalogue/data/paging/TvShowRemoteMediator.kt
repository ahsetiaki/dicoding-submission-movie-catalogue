package com.setiaki.moviecatalogue.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.setiaki.moviecatalogue.data.local.database.AppDatabase
import com.setiaki.moviecatalogue.data.local.entity.TvShowEntity
import com.setiaki.moviecatalogue.data.local.entity.TvShowPageEntity
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice.Companion.TMDB_STARTING_PAGE_INDEX
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.TvShowMapper
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class TvShowRemoteMediator @Inject constructor(
    private val webservice: TMDBWebservice,
    private val database: AppDatabase,
    private val tvShowMapper: TvShowMapper
) : RemoteMediator<Int, TvShowEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvShowEntity>
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
            val apiResponse = webservice.getTopRatedTvShows(page = page)
            val tvShowResponse = apiResponse.results
            val tvShowEntities = tvShowMapper.mapResponseListToEntityList(tvShowResponse)

            val endOfPaginationReached = tvShowResponse.isEmpty()
            val prevPage = if (page == TMDB_STARTING_PAGE_INDEX) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1
            val pageEntities = tvShowResponse.map {
                TvShowPageEntity(it.id, prevPage, nextPage)
            }

            with(database) {
                withTransaction {
                    tvShowPageDao().insertAll(pageEntities)
                    tvShowDao().insertTvShows(tvShowEntities)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getPageForLastItem(state: PagingState<Int, TvShowEntity>): TvShowPageEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { tvShow ->
                database.tvShowPageDao().getTvShowPageByTvShowId(tvShow.tvShowid)
            }
    }

    private suspend fun getPageForFirstItem(state: PagingState<Int, TvShowEntity>): TvShowPageEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { tvShow ->
                database.tvShowPageDao().getTvShowPageByTvShowId(tvShow.tvShowid)
            }
    }

    private suspend fun getClosestPageToCurrentPosition(state: PagingState<Int, TvShowEntity>): TvShowPageEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.tvShowid?.let { tvShowId ->
                database.tvShowPageDao().getTvShowPageByTvShowId(tvShowId)
            }
        }
    }

}
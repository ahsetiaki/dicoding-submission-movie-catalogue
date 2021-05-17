package com.setiaki.moviecatalogue.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.setiaki.moviecatalogue.data.local.entity.TvShowEntity


@Dao
interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTvShow(tvShow: TvShowEntity)

    @Update
    suspend fun updateTvShow(tvShow: TvShowEntity)

    @Query("SELECT * FROM tv_shows WHERE tv_show_id = :id")
    suspend fun getTvShow(id: Int): TvShowEntity

    @Query("SELECT * FROM tv_shows")
    fun getTopRatedTvShows(): PagingSource<Int, TvShowEntity>

    @Query("SELECT * FROM tv_shows WHERE is_favorited = 1")
    fun getFavoritedTvShow(): PagingSource<Int, TvShowEntity>

    @Query("DELETE FROM tv_shows")
    suspend fun clearTvShows()
}

// PAGING 2

//@Query("SELECT * FROM tv_shows")
//fun getTopRatedTvShows(): DataSource.Factory<Int, TvShowEntity>
//
//@Query("SELECT * FROM tv_shows WHERE is_favorited = 1")
//fun getFavoritedTvShow(): DataSource.Factory<Int, TvShowEntity>
package com.setiaki.moviecatalogue.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.setiaki.moviecatalogue.data.local.entity.TvShowPageEntity


@Dao
interface TvShowPageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvShowPageEntities: List<TvShowPageEntity>)

    @Query("SELECT * FROM tv_show_pages WHERE tv_show_id = :tvShowId")
    suspend fun getTvShowPageByTvShowId(tvShowId: Int): TvShowPageEntity?

    @Query("DELETE FROM tv_show_pages")
    suspend fun clearTvShowPages()
}
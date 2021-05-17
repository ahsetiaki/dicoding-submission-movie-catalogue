package com.setiaki.moviecatalogue.data.local.dao

import androidx.room.*
import com.setiaki.moviecatalogue.data.local.entity.GenreEntity


@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Delete
    suspend fun deleteGenres(genres: List<GenreEntity>)

    @Query("DELETE FROM genres")
    suspend fun clearGenres()
}
package com.setiaki.moviecatalogue.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.setiaki.moviecatalogue.data.local.entity.MoviePageEntity


@Dao
interface MoviePageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moviePageEntities: List<MoviePageEntity>)

    @Query("SELECT * FROM movie_pages WHERE movie_id = :movieId")
    suspend fun getMoviePageByMovieId(movieId: Int) : MoviePageEntity?

    @Query("DELETE FROM movie_pages")
    suspend fun clearMoviePages()
}
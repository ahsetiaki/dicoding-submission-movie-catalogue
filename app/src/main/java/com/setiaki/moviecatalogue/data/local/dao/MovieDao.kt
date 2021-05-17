package com.setiaki.moviecatalogue.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.setiaki.moviecatalogue.data.local.entity.MovieEntity


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE movie_id = :id")
    suspend fun getMovie(id: Int): MovieEntity

    @Query("SELECT * FROM movies")
    fun getTopRatedMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE is_favorited = 1")
    fun getFavoritedMovies(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearMovies()
}

// PAGING 2

//@Query("SELECT * FROM movies")
//fun getTopRatedMovies(): DataSource.Factory<Int, MovieEntity>
//
//@Query("SELECT * FROM movies WHERE is_favorited = 1")
//fun getFavoritedMovies(): DataSource.Factory<Int, MovieEntity>
package com.setiaki.moviecatalogue.data.local.dao

import androidx.room.*
import com.setiaki.moviecatalogue.data.local.entity.MovieGenreCrossRefEntity
import com.setiaki.moviecatalogue.data.local.entity.MovieWithGenre
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieGenreCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieGenreCrossRefs(crossRefs: List<MovieGenreCrossRefEntity>)

    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id = :movieId ")
    fun getMovieWithGenre(movieId: Int): Flow<MovieWithGenre>

    @Query("DELETE FROM movie_genre_cross_ref")
    suspend fun clearMovieGenreCrossRef()
}
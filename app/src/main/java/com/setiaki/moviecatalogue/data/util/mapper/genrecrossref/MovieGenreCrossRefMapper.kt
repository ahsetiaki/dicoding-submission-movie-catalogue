package com.setiaki.moviecatalogue.data.util.mapper.genrecrossref

import com.setiaki.moviecatalogue.data.local.entity.MovieGenreCrossRefEntity
import com.setiaki.moviecatalogue.data.remote.response.MovieResponse
import javax.inject.Inject

class MovieGenreCrossRefMapper @Inject constructor() :
    GenreCrossRefMapper<MovieResponse, MovieGenreCrossRefEntity> {

    override fun mapResponseToCrossRefs(response: MovieResponse): List<MovieGenreCrossRefEntity> {
        val crossRefList = mutableListOf<MovieGenreCrossRefEntity>()
        response.genres.forEach { genre ->
            val crossRef = MovieGenreCrossRefEntity(
                movieId = response.id,
                genreId = genre.id
            )
            crossRefList.add(crossRef)
        }
        return crossRefList
    }
}


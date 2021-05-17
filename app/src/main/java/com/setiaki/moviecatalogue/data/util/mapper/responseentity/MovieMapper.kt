package com.setiaki.moviecatalogue.data.util.mapper.responseentity

import com.setiaki.moviecatalogue.data.local.entity.MovieEntity
import com.setiaki.moviecatalogue.data.remote.response.MovieResponse
import javax.inject.Inject


class MovieMapper @Inject constructor() : ResponseToEntityMapper<MovieResponse, MovieEntity> {
    override fun mapResponseToEntity(response: MovieResponse): MovieEntity {
        return MovieEntity(
            movieId = response.id,
            title = response.title,
            posterPath = response.posterPath,
            overview = response.overview,
            releaseDate = response.releaseDate,
            voteAverage = response.voteAverage.toFloat(),
            isFavorited = false
        )
    }

    override fun mapResponseListToEntityList(responseList: List<MovieResponse>): List<MovieEntity> {
        return responseList.map { mapResponseToEntity(it) }
    }
}
package com.setiaki.moviecatalogue.data.util.mapper.responseentity

import com.setiaki.moviecatalogue.data.local.entity.TvShowEntity
import com.setiaki.moviecatalogue.data.remote.response.TvShowResponse
import javax.inject.Inject


class TvShowMapper @Inject constructor() : ResponseToEntityMapper<TvShowResponse, TvShowEntity> {
    override fun mapResponseToEntity(response: TvShowResponse): TvShowEntity {
        return TvShowEntity(
            tvShowid = response.id,
            title = response.title,
            posterPath = response.posterPath,
            overview = response.overview,
            releaseDate = response.releaseDate,
            voteAverage = response.voteAverage.toFloat(),
            isFavorited = false
        )
    }

    override fun mapResponseListToEntityList(responseList: List<TvShowResponse>): List<TvShowEntity> {
        return responseList.map { mapResponseToEntity(it) }
    }

}
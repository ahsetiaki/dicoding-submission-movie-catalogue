package com.setiaki.moviecatalogue.data.util.mapper.responseentity

import com.setiaki.moviecatalogue.data.local.entity.GenreEntity
import com.setiaki.moviecatalogue.data.remote.response.GenreResponse
import javax.inject.Inject


class GenreMapper @Inject constructor() : ResponseToEntityMapper<GenreResponse, GenreEntity> {
    override fun mapResponseToEntity(response: GenreResponse): GenreEntity {
        return GenreEntity(
            genreId = response.id,
            name = response.name
        )
    }

    override fun mapResponseListToEntityList(responseList: List<GenreResponse>): List<GenreEntity> {
        return responseList.map { mapResponseToEntity(it) }
    }

}
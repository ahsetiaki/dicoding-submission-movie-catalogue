package com.setiaki.moviecatalogue.data.util.mapper.genrecrossref

import com.setiaki.moviecatalogue.data.local.entity.TvShowGenreCrossRefEntity
import com.setiaki.moviecatalogue.data.remote.response.TvShowResponse
import javax.inject.Inject

class TvShowGenreCrossRefMapper @Inject constructor() :
    GenreCrossRefMapper<TvShowResponse, TvShowGenreCrossRefEntity> {

    override fun mapResponseToCrossRefs(response: TvShowResponse): List<TvShowGenreCrossRefEntity> {
        val crossRefList = mutableListOf<TvShowGenreCrossRefEntity>()
        response.genres.forEach { genre ->
            val crossRef = TvShowGenreCrossRefEntity(
                tvShowId = response.id,
                genreId = genre.id
            )
            crossRefList.add(crossRef)
        }
        return crossRefList
    }


}
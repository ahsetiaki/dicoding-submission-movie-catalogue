package com.setiaki.moviecatalogue.data.util.mapper.genrecrossref

interface GenreCrossRefMapper<Response, GenreCrossRefEntity> {
    fun mapResponseToCrossRefs(response: Response) : List<GenreCrossRefEntity>
}
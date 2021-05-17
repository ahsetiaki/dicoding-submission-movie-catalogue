package com.setiaki.moviecatalogue.data.util.mapper.responseentity


interface ResponseToEntityMapper<Response, Entity> {
    fun mapResponseToEntity(response: Response) : Entity
    fun mapResponseListToEntityList(responseList: List<Response>) : List<Entity>
}
package com.setiaki.moviecatalogue.data.repository

import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class CatalogueRepositoryTest {
    private lateinit var catalogueRepository: CatalogueRepository

    @Before
    fun setUp() {
        val webservice = TMDBWebservice.create()
        catalogueRepository = CatalogueRepository(webservice)
    }

    @Test
    fun testGetTopRatedMovies() {
        runBlocking {
            val defaultLimit = 20 // Sesuai TMDB API default limit
            val apiResponse = catalogueRepository.getTopRatedMovies()
            assertTrue(!apiResponse.isNullOrEmpty())
            assertTrue(apiResponse.size == defaultLimit)
        }
    }

    @Test
    fun testGetTopRatedTVShows() {
        runBlocking {
            val defaultLimit = 20 // Sesuai TMDB API default limit
            val apiResponse = catalogueRepository.getTopRatedTVShows()
            assertTrue(!apiResponse.isNullOrEmpty())
            assertTrue(apiResponse.size == defaultLimit)
        }
    }
}
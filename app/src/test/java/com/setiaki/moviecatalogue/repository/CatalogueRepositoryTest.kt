package com.setiaki.moviecatalogue.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CatalogueRepositoryTest {
    private lateinit var catalogueRepository: CatalogueRepository

    @Before
    fun setUp() {
        catalogueRepository = CatalogueRepository()
    }

    @Test
    fun testGetTopRatedMovies() {
        runBlocking {
            val defaultLimit = 20 // Sesuai TMDB API default limit
            val apiResponse = catalogueRepository.getTopRatedMovies()
            assertTrue(!apiResponse.results.isNullOrEmpty())
            assertTrue(apiResponse.results.size == defaultLimit)
        }
    }

    @Test
    fun testGetTopRatedTVShows() {
        runBlocking {
            val defaultLimit = 20 // Sesuai TMDB API default limit
            val apiResponse = catalogueRepository.getTopRatedTVShows()
            assertTrue(!apiResponse.results.isNullOrEmpty())
            assertTrue(apiResponse.results.size == defaultLimit)
        }
    }
}
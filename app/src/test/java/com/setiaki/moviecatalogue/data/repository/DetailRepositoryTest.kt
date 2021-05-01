package com.setiaki.moviecatalogue.data.repository

import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


class DetailRepositoryTest {
    private lateinit var detailRepository: DetailRepository

    @Before
    fun setUp() {
        val webservice = TMDBWebservice.create()
        detailRepository = DetailRepository(webservice)
    }

    @Test
    fun testGetMovieDetail() {
        runBlocking {
            val testId = 278
            val apiResponse = detailRepository.getMovieDetail(testId)
            assertNotNull(apiResponse)
            assertEquals("The Shawshank Redemption", apiResponse.title)
        }
    }

    @Test
    fun testGetTVShowDetail() {
        runBlocking {
            val testId = 100
            val apiResponse = detailRepository.getTVShowDetail(testId)
            assertNotNull(apiResponse)
            assertEquals("I Am Not an Animal", apiResponse.title)
        }
    }
}
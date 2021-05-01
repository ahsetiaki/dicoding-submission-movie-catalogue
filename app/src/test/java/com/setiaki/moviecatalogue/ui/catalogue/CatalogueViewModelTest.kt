package com.setiaki.moviecatalogue.ui.catalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.setiaki.moviecatalogue.CoroutinesTestRule
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.data.remote.response.MovieDetailResponse
import com.setiaki.moviecatalogue.data.remote.response.TvShowDetailResponse
import com.setiaki.moviecatalogue.data.repository.CatalogueRepository
import com.setiaki.moviecatalogue.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CatalogueViewModelTest {
    //    private val catalogueViewModel by lazy { CatalogueViewModel() }
    private val webservice = TMDBWebservice.create()
    private val catalogueRepository = CatalogueRepository(webservice)
    private val catalogueViewModel by lazy { CatalogueViewModel(catalogueRepository) }

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun testGetTopRatedMovies() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            catalogueViewModel.getTopRatedMovies()
            catalogueViewModel.topRatedMovies.observeForever { }
            val liveDataValue =
                catalogueViewModel.topRatedMovies.getOrAwaitValue() as List<MovieDetailResponse>

            val defaultLimit = 20 // Sesuai TMDB API default limit
            assertTrue(!liveDataValue.isNullOrEmpty())
            assertTrue(liveDataValue.size == defaultLimit)
        }
    }

    @Test
    fun testGetTopRatedTvShows() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            catalogueViewModel.getTopRatedTvShows()
            catalogueViewModel.topRatedTvShows.observeForever { }
            val liveDataValue =
                catalogueViewModel.topRatedTvShows.getOrAwaitValue() as List<TvShowDetailResponse>

            val defaultLimit = 20 // Sesuai TMDB API default limit
            assertTrue(!liveDataValue.isNullOrEmpty())
            assertTrue(liveDataValue.size == defaultLimit)
        }
    }


}
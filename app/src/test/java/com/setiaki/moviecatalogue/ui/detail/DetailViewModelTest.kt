package com.setiaki.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.setiaki.moviecatalogue.CoroutinesTestRule
import com.setiaki.moviecatalogue.getOrAwaitValue
import com.setiaki.moviecatalogue.response.MovieDetailResponse
import com.setiaki.moviecatalogue.response.TvShowDetailResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    private val detailViewModel by lazy { DetailViewModel() }

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun testGetMovieDetail() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val testId = 278
            detailViewModel.getMovieDetail(testId)
            detailViewModel.movieDetail.observeForever { }
            val liveDataValue = detailViewModel.movieDetail.getOrAwaitValue() as MovieDetailResponse

            assertNotNull(liveDataValue)
            assertEquals("The Shawshank Redemption", liveDataValue.title)
        }
    }

    @Test
    fun testGetTvShowDetail() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val testId = 100
            detailViewModel.getTvShowDetail(testId)
            detailViewModel.tvShowDetail.observeForever { }
            val liveDataValue = detailViewModel.tvShowDetail.getOrAwaitValue() as TvShowDetailResponse

            assertNotNull(liveDataValue)
            assertEquals("I Am Not an Animal", liveDataValue.title)
        }
    }
}
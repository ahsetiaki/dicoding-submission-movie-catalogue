package com.setiaki.moviecatalogue.ui.catalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.setiaki.moviecatalogue.CoroutineTestRule
import com.setiaki.moviecatalogue.data.repository.CatalogueRepository
import com.setiaki.moviecatalogue.DummyData
import com.setiaki.moviecatalogue.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CatalogueViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val catalogueRepository = mock(CatalogueRepository::class.java)
    private val catalogueViewModel by lazy { CatalogueViewModel(catalogueRepository) }

    @Test
    fun testGetTopRatedMovies() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val flowDummyData = flowOf(PagingData.from(DummyData.getTopRatedMoviesEntity()))
        whenever(catalogueRepository.getTopRatedMovies()).thenReturn(flowDummyData)

        val result = catalogueViewModel.getTopRatedMovies().getOrAwaitValue()

        assertNotNull(result)
    }

    @Test
    fun testGetTopRatedTvShows() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val flowDummyData = flowOf(PagingData.from(DummyData.getTopRatedTvShowsEntity()))
        whenever(catalogueRepository.getTopRatedTVShows()).thenReturn(flowDummyData)

        val result = catalogueViewModel.getTopRatedTvShows().getOrAwaitValue()

        assertNotNull(result)
    }

}
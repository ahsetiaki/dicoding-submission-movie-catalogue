package com.setiaki.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.setiaki.moviecatalogue.CoroutineTestRule
import com.setiaki.moviecatalogue.data.local.dao.MovieGenreCrossRefDao
import com.setiaki.moviecatalogue.data.local.dao.TvShowGenreCrossRefDao
import com.setiaki.moviecatalogue.data.local.database.AppDatabase
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.data.repository.DetailRepository
import com.setiaki.moviecatalogue.DummyData
import com.setiaki.moviecatalogue.data.util.mapper.genrecrossref.MovieGenreCrossRefMapper
import com.setiaki.moviecatalogue.data.util.mapper.genrecrossref.TvShowGenreCrossRefMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.GenreMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.MovieMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.TvShowMapper
import com.setiaki.moviecatalogue.data.util.networkbound.Resource
import com.setiaki.moviecatalogue.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val webservice = mock(TMDBWebservice::class.java)
    private val database = mock(AppDatabase::class.java)
    private val movieGenreCrossRefDao = mock(MovieGenreCrossRefDao::class.java)
    private val tvShowGenreCrossRefDao = mock(TvShowGenreCrossRefDao::class.java)
    private val movieMapper = mock(MovieMapper::class.java)
    private val tvShowMapper = mock(TvShowMapper::class.java)
    private val genreMapper = mock(GenreMapper::class.java)
    private val movieGenreCrossRefMapper = mock(MovieGenreCrossRefMapper::class.java)
    private val tvShowGenreCrosRefMapper = mock(TvShowGenreCrossRefMapper::class.java)
    private val detailRepository = DetailRepository(
        webservice,
        database,
        movieMapper,
        tvShowMapper, genreMapper,
        movieGenreCrossRefMapper,
        tvShowGenreCrosRefMapper
    )

    private val detailViewModel by lazy { DetailViewModel(detailRepository) }

    @Test
    fun testGetMovieDetail() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val movieId = 122
        val expectedMovieTitle = "Spirited Away"
        val dummyFlowEntity = flowOf(DummyData.getMovieWithGenre())

        whenever(database.movieGenreCrossRefDao()).thenReturn(movieGenreCrossRefDao)
        whenever(database.movieGenreCrossRefDao().getMovieWithGenre(movieId)).thenReturn(
            dummyFlowEntity
        )

        val result = detailViewModel.getMovieDetail(movieId).getOrAwaitValue()
        assertEquals(Resource.Success::class.java, result::class.java)
        assertEquals(expectedMovieTitle, result.data?.movie?.title)
    }

    @Test
    fun testGetTvShowDetail() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val tvShowId = 100
        val expectedTvShowTitle = "I Am Not an Animal"
        val dummyFlowEntity = flowOf(DummyData.getTvShowWithGenre())

        whenever(database.tvShowGenreCrossRefDao()).thenReturn(tvShowGenreCrossRefDao)
        whenever(database.tvShowGenreCrossRefDao().getTvShowWithGenre(tvShowId)).thenReturn(
            dummyFlowEntity
        )

        val result = detailViewModel.getTvShowDetail(tvShowId).getOrAwaitValue()
        assertEquals(Resource.Success::class.java, result::class.java)
        assertEquals(expectedTvShowTitle, result.data?.tvShow?.title)
    }
}
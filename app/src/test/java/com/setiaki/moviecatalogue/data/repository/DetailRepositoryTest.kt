package com.setiaki.moviecatalogue.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.setiaki.moviecatalogue.CoroutineTestRule
import com.setiaki.moviecatalogue.data.local.dao.MovieDao
import com.setiaki.moviecatalogue.data.local.dao.MovieGenreCrossRefDao
import com.setiaki.moviecatalogue.data.local.dao.TvShowDao
import com.setiaki.moviecatalogue.data.local.dao.TvShowGenreCrossRefDao
import com.setiaki.moviecatalogue.data.local.database.AppDatabase
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.DummyData
import com.setiaki.moviecatalogue.data.util.mapper.genrecrossref.MovieGenreCrossRefMapper
import com.setiaki.moviecatalogue.data.util.mapper.genrecrossref.TvShowGenreCrossRefMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.GenreMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.MovieMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.TvShowMapper
import com.setiaki.moviecatalogue.data.util.networkbound.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime


@ExperimentalTime
@ExperimentalCoroutinesApi
class DetailRepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val webservice = mock(TMDBWebservice::class.java)
    private val database = mock(AppDatabase::class.java)
    private val movieDao = mock(MovieDao::class.java)
    private val tvShowDao = mock(TvShowDao::class.java)
    private val movieGenreCrossRefDao = mock(MovieGenreCrossRefDao::class.java)
    private val tvShowGenreCrossRefDao = mock(TvShowGenreCrossRefDao::class.java)
    private val movieMapper = mock(MovieMapper::class.java)
    private val tvShowMapper = mock(TvShowMapper::class.java)
    private val genreMapper = mock(GenreMapper::class.java)
    private val movieGenreCrossRefMapper = mock(MovieGenreCrossRefMapper::class.java)
    private val tvShowGenreCrossRefMapper = mock(TvShowGenreCrossRefMapper::class.java)
    private val detailRepository = DetailRepository(
        webservice,
        database,
        movieMapper,
        tvShowMapper,
        genreMapper,
        movieGenreCrossRefMapper,
        tvShowGenreCrossRefMapper
    )

    // Hanya tes data dari database karena tidak memasukan data ke database
    @Test
    fun testGetMovieDetail() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val movieId = 122
        val expectedMovieTitle = "Spirited Away"
        val dummyFlowEntity = flowOf(DummyData.getMovieWithGenre())

        whenever(database.movieGenreCrossRefDao()).thenReturn(movieGenreCrossRefDao)
        whenever(database.movieGenreCrossRefDao().getMovieWithGenre(movieId)).thenReturn(
            dummyFlowEntity
        )

        detailRepository.getMovieDetail(movieId).test {
            val flowItem1 = expectItem()
            assertEquals(Resource.Success::class.java, flowItem1::class.java)
            assertEquals(expectedMovieTitle, flowItem1.data?.movie?.title)
            expectComplete()
        }
    }

    // Hanya tes data dari database karena tidak memasukan data ke database
    @Test
    fun testGetTVShowDetail() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val tvShowId = 100
        val expectedTvShowTitle = "I Am Not an Animal"
        val dummyFlowEntity = flowOf(DummyData.getTvShowWithGenre())

        whenever(database.tvShowGenreCrossRefDao()).thenReturn(tvShowGenreCrossRefDao)
        whenever(database.tvShowGenreCrossRefDao().getTvShowWithGenre(tvShowId)).thenReturn(
            dummyFlowEntity
        )

        detailRepository.getTVShowDetail(tvShowId).test {
            val flowItem1 = expectItem()
            assertEquals(Resource.Success::class.java, flowItem1::class.java)
            assertEquals(expectedTvShowTitle, flowItem1.data?.tvShow?.title)
            expectComplete()
        }
    }


    @Test
    fun testToggleMovieDetailFavoriteStatus() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val dummyEntity = DummyData.getMovieEntity()
        whenever(database.movieDao()).thenReturn(movieDao)
        whenever(database.movieDao().getMovie(anyInt())).thenReturn(dummyEntity)

        assertFalse(dummyEntity.isFavorited)
        detailRepository.toggleMovieDetailFavoriteStatus(dummyEntity.movieId)
        assertTrue(dummyEntity.isFavorited)
    }


    @Test
    fun testToggleTvShowDetailFavoriteStatus() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val dummyEntity = DummyData.getTvShowEntity()
        whenever(database.tvShowDao()).thenReturn(tvShowDao)
        whenever(database.tvShowDao().getTvShow(anyInt())).thenReturn(dummyEntity)

        assertFalse(dummyEntity.isFavorited)
        detailRepository.toggleTvShowDetailFavoriteStatus(dummyEntity.tvShowid)
        assertTrue(dummyEntity.isFavorited)
    }
}






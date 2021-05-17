package com.setiaki.moviecatalogue.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult.Page
import app.cash.turbine.test
import com.setiaki.moviecatalogue.CoroutineTestRule
import com.setiaki.moviecatalogue.DummyData
import com.setiaki.moviecatalogue.data.local.dao.MovieDao
import com.setiaki.moviecatalogue.data.local.dao.TvShowDao
import com.setiaki.moviecatalogue.data.local.database.AppDatabase
import com.setiaki.moviecatalogue.data.local.entity.MovieEntity
import com.setiaki.moviecatalogue.data.local.entity.TvShowEntity
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.MovieMapper
import com.setiaki.moviecatalogue.data.util.mapper.responseentity.TvShowMapper
import com.setiaki.moviecatalogue.ui.catalogue.adapter.MovieAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime


@ExperimentalTime
@ExperimentalCoroutinesApi
class CatalogueRepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val webservice = mock(TMDBWebservice::class.java)
    private val database = mock(AppDatabase::class.java)
    private val movieDao = mock(MovieDao::class.java)
    private val tvShowDao = mock(TvShowDao::class.java)
    private val movieMapper = mock(MovieMapper::class.java)
    private val tvShowMapper = mock(TvShowMapper::class.java)
    private val catalogueRepository = CatalogueRepository(
        webservice, database, movieMapper, tvShowMapper
    )

    /*  Paging 2 dan Paging 3 belum menyediakan fitur testing yang baik.
    **  Fitur testing untuk paging masih sangat terbatas,
    **  https://stackoverflow.com/questions/65112403/android-paging-3-get-list-of-data-from-pagingdatat-object
    */

    @Test
    fun testGetTopRatedMovies() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val mockPagingSource = mock(PagingSource::class.java) as PagingSource<Int, MovieEntity>
        val mockPage = Page(
            data = DummyData.getTopRatedMoviesEntity(),
            prevKey = null,
            nextKey = 2
        )
        whenever(database.movieDao()).thenReturn(movieDao)
        whenever(database.movieDao().getTopRatedMovies()).thenReturn(mockPagingSource)
        whenever(
            mockPagingSource.load(
                Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        ).thenReturn(mockPage)

        /* Hanya menuguji apakah fungsi Pager mengembalikan PagingData saat PagingSource mendapat trigger load
        ** Fitur testing untuk PagingData masih terbatas
        */


        catalogueRepository.getTopRatedMovies().test {
            val result = expectItem()
            assertNotNull(result)
        }
    }

    @Test
    fun testGetTopRatedTVShows() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val mockPagingSource = mock(PagingSource::class.java) as PagingSource<Int, TvShowEntity>
        val mockPage = Page(
            data = DummyData.getTopRatedTvShowsEntity(),
            prevKey = null,
            nextKey = 2
        )
        whenever(database.tvShowDao()).thenReturn(tvShowDao)
        whenever(database.tvShowDao().getTopRatedTvShows()).thenReturn(mockPagingSource)
        whenever(
            mockPagingSource.load(
                Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        ).thenReturn(mockPage)

        /* Hanya menuguji apakah fungsi Pager mengembalikan PagingData saat PagingSource mendapat trigger load
        ** Fitur testing untuk PagingData masih terbatas
        */

        catalogueRepository.getTopRatedTVShows().test {
            val result = expectItem()
            assertNotNull(result)
        }

    }

    @Test
    fun testGetFavoritedMovies() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val dummyData =
            DummyData.getTopRatedMoviesEntity().apply { forEach { it.isFavorited = true } }
        val mockPagingSource = mock(PagingSource::class.java) as PagingSource<Int, MovieEntity>
        val mockPage = Page(
            data = dummyData,
            prevKey = null,
            nextKey = 2
        )

        whenever(database.movieDao()).thenReturn(movieDao)
        whenever(database.movieDao().getFavoritedMovies()).thenReturn(mockPagingSource)
        whenever(
            mockPagingSource.load(
                Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        ).thenReturn(mockPage)

        /* Hanya menuguji apakah fungsi Pager mengembalikan PagingData saat PagingSource mendapat trigger load
        ** Fitur testing untuk PagingData masih terbatas
        */
        catalogueRepository.getFavoritedMovies().test {
            val result = expectItem()
            assertNotNull(result)
        }
    }

    @Test
    fun testGetFavoritedTvShows() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val dummyData =
            DummyData.getTopRatedTvShowsEntity().apply { forEach { it.isFavorited = true } }
        val mockPagingSource = mock(PagingSource::class.java) as PagingSource<Int, TvShowEntity>
        val mockPage = Page(
            data = dummyData,
            prevKey = null,
            nextKey = 2
        )

        whenever(database.tvShowDao()).thenReturn(tvShowDao)
        whenever(database.tvShowDao().getFavoritedTvShow()).thenReturn(mockPagingSource)
        whenever(
            mockPagingSource.load(
                Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        ).thenReturn(mockPage)

        /* Hanya menuguji apakah fungsi Pager mengembalikan PagingData saat PagingSource mendapat trigger load
        ** Fitur testing untuk PagingData masih terbatas
        */
        catalogueRepository.getFavoritedTvShows().test {
            val result = expectItem()
            assertNotNull(result)
        }
    }
}

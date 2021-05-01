package com.setiaki.moviecatalogue.ui.catalogue

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setiaki.moviecatalogue.data.remote.response.MovieDetailResponse
import com.setiaki.moviecatalogue.data.remote.response.TvShowDetailResponse
import com.setiaki.moviecatalogue.data.repository.CatalogueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogueViewModel @Inject constructor(private val catalogueRepository: CatalogueRepository) :
    ViewModel() {
    val topRatedMovies = MutableLiveData<List<MovieDetailResponse>>()

    val topRatedTvShows = MutableLiveData<List<TvShowDetailResponse>>()

    fun getTopRatedMovies() {
        viewModelScope.launch {
            topRatedMovies.value = catalogueRepository.getTopRatedMovies()
        }
    }

    fun getTopRatedTvShows() {
        viewModelScope.launch {
            topRatedTvShows.value = catalogueRepository.getTopRatedTVShows()
        }
    }
}
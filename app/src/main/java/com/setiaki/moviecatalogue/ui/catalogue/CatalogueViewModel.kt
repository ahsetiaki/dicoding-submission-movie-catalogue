package com.setiaki.moviecatalogue.ui.catalogue

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setiaki.moviecatalogue.repository.CatalogueRepository
import com.setiaki.moviecatalogue.response.MovieDetailResponse
import com.setiaki.moviecatalogue.response.TvShowDetailResponse
import kotlinx.coroutines.launch

class CatalogueViewModel : ViewModel() {
    private val catalogueRepository = CatalogueRepository()

    val topRatedMovies = MutableLiveData<List<MovieDetailResponse>>()

    val topRatedTvShows = MutableLiveData<List<TvShowDetailResponse>>()


    fun getTopRatedMovies() {
        viewModelScope.launch {
            topRatedMovies.value = catalogueRepository.getTopRatedMovies().results
        }
    }

    fun getTopRatedTvShows() {
        viewModelScope.launch {
            topRatedTvShows.value = catalogueRepository.getTopRatedTVShows().results
        }
    }
}
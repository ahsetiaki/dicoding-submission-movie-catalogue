package com.setiaki.moviecatalogue.ui.catalogue

import androidx.lifecycle.LiveData
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
    private val _topRatedMovies = MutableLiveData<List<MovieDetailResponse>>()
    val topRatedMovies: LiveData<List<MovieDetailResponse>> = _topRatedMovies

    private val _topRatedTvShows = MutableLiveData<List<TvShowDetailResponse>>()
    val topRatedTvShows: LiveData<List<TvShowDetailResponse>> = _topRatedTvShows

    fun getTopRatedMovies() {
        viewModelScope.launch {
            _topRatedMovies.value = catalogueRepository.getTopRatedMovies()
        }
    }

    fun getTopRatedTvShows() {
        viewModelScope.launch {
            _topRatedTvShows.value = catalogueRepository.getTopRatedTVShows()
        }
    }
}
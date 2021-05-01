package com.setiaki.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setiaki.moviecatalogue.data.remote.response.MovieDetailResponse
import com.setiaki.moviecatalogue.data.remote.response.TvShowDetailResponse
import com.setiaki.moviecatalogue.data.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository) :
    ViewModel() {
    private val _movieDetail = MutableLiveData<MovieDetailResponse>()
    val movieDetail: LiveData<MovieDetailResponse> = _movieDetail

    private val _tvShowDetail = MutableLiveData<TvShowDetailResponse>()
    val tvShowDetail: LiveData<TvShowDetailResponse> = _tvShowDetail

    fun getMovieDetail(itemId: Int) {
        viewModelScope.launch {
            _movieDetail.value = detailRepository.getMovieDetail(itemId)
        }
    }

    fun getTvShowDetail(itemId: Int) {
        viewModelScope.launch {
            _tvShowDetail.value = detailRepository.getTVShowDetail(itemId)
        }
    }

}
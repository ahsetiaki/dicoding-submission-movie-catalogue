package com.setiaki.moviecatalogue.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setiaki.moviecatalogue.repository.DetailRepository
import com.setiaki.moviecatalogue.response.MovieDetailResponse
import com.setiaki.moviecatalogue.response.TvShowDetailResponse
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val detailRepository = DetailRepository()

    val movieDetail = MutableLiveData<MovieDetailResponse>()

    val tvShowDetail = MutableLiveData<TvShowDetailResponse>()

    fun getMovieDetail(itemId: Int) {
        viewModelScope.launch {
            movieDetail.value = detailRepository.getMovieDetail(itemId)
        }
    }

    fun getTvShowDetail(itemId: Int) {
        viewModelScope.launch {
            tvShowDetail.value = detailRepository.getTVShowDetail(itemId)
        }
    }

}
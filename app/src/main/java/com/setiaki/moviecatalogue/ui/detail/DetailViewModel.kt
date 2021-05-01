package com.setiaki.moviecatalogue.ui.detail

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
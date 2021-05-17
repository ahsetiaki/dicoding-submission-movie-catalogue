package com.setiaki.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.setiaki.moviecatalogue.data.local.entity.MovieWithGenre
import com.setiaki.moviecatalogue.data.local.entity.TvShowWithGenre
import com.setiaki.moviecatalogue.data.repository.DetailRepository
import com.setiaki.moviecatalogue.data.util.networkbound.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository) :
    ViewModel() {

    fun getMovieDetail(itemId: Int): LiveData<Resource<MovieWithGenre>> {
        return detailRepository.getMovieDetail(itemId).asLiveData()
    }

    fun getTvShowDetail(itemId: Int): LiveData<Resource<TvShowWithGenre>> {
        return detailRepository.getTVShowDetail(itemId).asLiveData()
    }

    fun toggleMovieFavoriteStatus(itemId: Int) {
        viewModelScope.launch {
            detailRepository.toggleMovieDetailFavoriteStatus(itemId)
        }

    }

    fun toggleTvShowFavoriteStatus(itemId: Int) {
        viewModelScope.launch {
            detailRepository.toggleTvShowDetailFavoriteStatus(itemId)
        }
    }

}
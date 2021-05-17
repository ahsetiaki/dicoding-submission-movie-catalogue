package com.setiaki.moviecatalogue.ui.catalogue

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.setiaki.moviecatalogue.data.local.entity.MovieEntity
import com.setiaki.moviecatalogue.data.local.entity.TvShowEntity
import com.setiaki.moviecatalogue.data.repository.CatalogueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatalogueViewModel @Inject constructor(private val catalogueRepository: CatalogueRepository) :
    ViewModel() {

    fun getTopRatedMovies(): LiveData<PagingData<MovieEntity>> {
        return catalogueRepository.getTopRatedMovies().asLiveData().cachedIn(viewModelScope)
    }

    fun getTopRatedTvShows(): LiveData<PagingData<TvShowEntity>> {
        return catalogueRepository.getTopRatedTVShows().asLiveData().cachedIn(viewModelScope)
    }

    fun getFavoritedMovies(): LiveData<PagingData<MovieEntity>> {
        return catalogueRepository.getFavoritedMovies().asLiveData().cachedIn(viewModelScope)
    }

    fun getFavoritedTvShows(): LiveData<PagingData<TvShowEntity>> {
        return catalogueRepository.getFavoritedTvShows().asLiveData().cachedIn(viewModelScope)
    }

}
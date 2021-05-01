package com.setiaki.moviecatalogue.di

import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {
    @Singleton
    @Provides
    fun provideTMDBWebservice(): TMDBWebservice {
        return TMDBWebservice.create()
    }
}
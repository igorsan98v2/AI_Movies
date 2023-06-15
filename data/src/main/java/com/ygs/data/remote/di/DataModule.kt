package com.ygs.data.remote.di

import com.ygs.common.Constants
import com.ygs.data.remote.MoviesApiService
import com.ygs.data.remote.MoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMoviesApiService(retrofit: Retrofit): MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRemoteDataSource(apiService: MoviesApiService): MoviesRemoteDataSource {
        return MoviesRemoteDataSource(apiService)
    }
}
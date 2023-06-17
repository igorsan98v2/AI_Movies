package com.ygs.data.remote.di

import com.ygs.data.remote.MoviesApiService
import com.ygs.data.remote.MoviesRemoteDataSource
import com.ygs.data.remote.repository.MoviesRepositoryImpl
import com.ygs.data.remote.usecase.GetMovieDetailsUseCaseImpl
import com.ygs.data.remote.usecase.GetMoviesUseCaseImpl
import com.ygs.domain.repository.MoviesRepository
import com.ygs.domain.usecase.GetMovieDetailsUseCase
import com.ygs.domain.usecase.GetMoviesUseCase
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

    @Provides
    @Singleton
    fun provideMoviesRepository(remoteDataSource: MoviesRemoteDataSource): MoviesRepository {
        return MoviesRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(moviesRepository: MoviesRepository): GetMoviesUseCase {
        return GetMoviesUseCaseImpl(moviesRepository)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(moviesRepository: MoviesRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCaseImpl(moviesRepository)
    }
}
package com.ygs.data.remote.usecase

import com.ygs.domain.entities.MovieDetails
import com.ygs.domain.repository.MoviesRepository
import com.ygs.domain.usecase.GetMovieDetailsUseCase
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(private val repository: MoviesRepository) :
    GetMovieDetailsUseCase {
    override suspend fun getMovieDetails(movieId: String): MovieDetails {
        return repository.getMovieDetails(movieId)
    }
}
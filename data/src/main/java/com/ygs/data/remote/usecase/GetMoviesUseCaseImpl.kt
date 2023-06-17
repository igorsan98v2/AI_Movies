package com.ygs.data.remote.usecase

import com.ygs.domain.entities.MovieSummary
import com.ygs.domain.repository.MoviesRepository
import com.ygs.domain.usecase.GetMoviesUseCase

class GetMoviesUseCaseImpl(private val moviesRepository: MoviesRepository) : GetMoviesUseCase {
    override suspend fun getMovies(): List<MovieSummary> {
        return moviesRepository.getMovies()
    }
}
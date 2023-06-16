package com.ygs.domain.usecase

import com.ygs.domain.entities.MovieSummary

interface GetMoviesUseCase {
    suspend fun getMovies(): List<MovieSummary>
}
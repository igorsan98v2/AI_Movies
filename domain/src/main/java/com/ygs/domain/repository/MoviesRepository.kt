package com.ygs.domain.repository

import com.ygs.domain.entities.MovieDetails
import com.ygs.domain.entities.MovieSummary
import com.ygs.domain.usecase.GetMovieDetailsUseCase
import com.ygs.domain.usecase.GetMoviesUseCase

interface MoviesRepository{
    suspend fun getMovieDetails(movieId: String): MovieDetails
    suspend fun getMovies(): List<MovieSummary>
}


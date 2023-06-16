package com.ygs.domain.usecase

import com.ygs.domain.entities.MovieDetails

interface GetMovieDetailsUseCase {
    suspend fun getMovieDetails(movieId: String): MovieDetails
}
package com.ygs.data.remote

import com.ygs.data.remote.models.MovieDetailsResponse
import com.ygs.data.remote.models.MovieItemResponse

class MoviesRemoteDataSource(private val apiService: MoviesApiService) {

    suspend fun getMovies(): List<MovieItemResponse> {
        return apiService.getMovies()
    }

    suspend fun getMovieDetails(movieId: String): MovieDetailsResponse {
        return apiService.getMovieDetails(movieId)
    }
}
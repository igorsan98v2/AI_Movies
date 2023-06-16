package com.ygs.data.remote

import com.ygs.data.remote.models.MovieDetailResponse
import com.ygs.data.remote.models.MovieItemResponse

class MoviesRemoteDataSource(private val apiService: MoviesApiService) {

    suspend fun getMovies(): List<MovieItemResponse> {
        return apiService.getMovies()
    }

    suspend fun getMovieDetails(movieId: String): MovieDetailResponse {
        return apiService.getMovieDetails(movieId)
    }
}
package com.ygs.data.remote

import com.ygs.data.remote.models.MovieDetail
import com.ygs.data.remote.models.MovieItem

class MoviesRemoteDataSource(private val apiService: MoviesApiService) {

    suspend fun getMovies(): List<MovieItem> {
        return apiService.getMovies()
    }

    suspend fun getMovieDetails(movieId: String): MovieDetail {
        return apiService.getMovieDetails(movieId)
    }
}
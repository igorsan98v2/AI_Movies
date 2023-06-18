package com.ygs.data.remote

import com.ygs.data.remote.models.MovieDetailsResponse
import com.ygs.data.remote.models.MovieItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movies")
    suspend fun getMovies(): List<MovieItemResponse>

    @GET("movieDetails")
    suspend fun getMovieDetails(@Query("id") movieId: String): MovieDetailsResponse
}
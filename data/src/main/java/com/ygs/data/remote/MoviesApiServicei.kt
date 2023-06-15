package com.ygs.data.remote

import com.ygs.data.remote.models.MovieDetail
import com.ygs.data.remote.models.MovieItem
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movies")
    suspend fun getMovies(): List<MovieItem>

    @GET("movieDetails")
    suspend fun getMovieDetails(@Query("id") movieId: String): MovieDetail
}
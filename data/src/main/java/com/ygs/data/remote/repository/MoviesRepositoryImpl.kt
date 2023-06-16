package com.ygs.data.remote.repository

import com.ygs.data.remote.MoviesRemoteDataSource
import com.ygs.domain.entities.MovieDetails
import com.ygs.domain.entities.MovieSummary
import com.ygs.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val remoteDataSource: MoviesRemoteDataSource) :
    MoviesRepository {

    override suspend fun getMovies(): List<MovieSummary> {
       return remoteDataSource.getMovies().map { response ->
            MovieSummary(
                id = response.id,
                name = response.name,
                price = response.price
            )
        }
    }

    override suspend fun getMovieDetails(movieId: String): MovieDetails {
        return remoteDataSource.getMovieDetails(movieId).let { response ->
             MovieDetails(
                imageUrl = response.imageUrl,
                meta = response.meta,
                name = response.name,
                price = response.price,
                rating = response.rating,
                synopsis = response.synopsis
            )
        }
    }
}
package com.ygs.feature_movie_details.mvi

sealed interface MovieDetailsActions {
    data class FetchMovieDetails(val movieId: String) : MovieDetailsActions

    data class RefreshMovieDetails(val movieId: String) : MovieDetailsActions
}
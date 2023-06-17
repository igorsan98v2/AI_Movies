package com.ygs.feature_movie_details.mvi

import com.ygs.feature_movie_details.models.UIMovieDetails

sealed interface MovieDetailsState {
    object Idle : MovieDetailsState
    object Loading : MovieDetailsState

    object Refreshing: MovieDetailsState
    data class Success(val movie: UIMovieDetails) : MovieDetailsState
    data class Error(val message: String) : MovieDetailsState
}
package com.ygs.feature_movies_list.mvi

import com.ygs.domain.entities.MovieSummary

sealed interface MovieListState {
    object Idle : MovieListState
    object Loading : MovieListState

    object Refreshing: MovieListState
    data class Success(val movies: List<MovieSummary>) : MovieListState
    data class Error(val message: String) : MovieListState
}
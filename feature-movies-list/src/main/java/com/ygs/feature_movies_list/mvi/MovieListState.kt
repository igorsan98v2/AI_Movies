package com.ygs.feature_movies_list.mvi

import com.ygs.feature_movies_list.models.UIMovieSummary

sealed interface MovieListState {
    object Idle : MovieListState
    object Loading : MovieListState

    object Refreshing: MovieListState
    data class Success(val movies: List<UIMovieSummary>) : MovieListState
    data class Error(val message: String) : MovieListState
}
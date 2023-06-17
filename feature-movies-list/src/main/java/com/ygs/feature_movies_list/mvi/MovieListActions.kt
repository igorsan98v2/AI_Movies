package com.ygs.feature_movies_list.mvi

sealed interface MovieListActions {
    object FetchMovies : MovieListActions
    object RefreshMovies : MovieListActions
}
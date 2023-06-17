package com.ygs.feature_movies_list.mvi

sealed interface MovieListActions {
    object LoadMovies : MovieListActions
    object RefreshMovies : MovieListActions
}
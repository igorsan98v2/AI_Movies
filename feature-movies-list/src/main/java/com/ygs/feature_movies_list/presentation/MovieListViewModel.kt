package com.ygs.feature_movies_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ygs.domain.usecase.GetMoviesUseCase
import com.ygs.feature_movies_list.mvi.MovieListActions
import com.ygs.feature_movies_list.mvi.MovieListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<MovieListState>(MovieListState.Idle)
    val state: StateFlow<MovieListState> = _state

    fun handleAction(action: MovieListActions) {
        when (action) {
            MovieListActions.FetchMovies -> fetchMovies()
            MovieListActions.RefreshMovies -> refreshMovies()
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _state.value = MovieListState.Loading
            try {
                val result = getMoviesUseCase.getMovies()
                _state.value = MovieListState.Success(result)
            } catch (e: Exception) {
                _state.value = MovieListState.Error("Failed to load movies")
            }
        }

    }

    private fun refreshMovies() {
        _state.value = MovieListState.Refreshing
        viewModelScope.launch {
            try {
                val movies = getMoviesUseCase.getMovies()
                _state.value = MovieListState.Success(movies)
            } catch (e: Exception) {
                _state.value = MovieListState.Error("Failed to refresh movies")
            }
        }
    }
}
package com.ygs.feature_movies_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ygs.domain.usecase.GetMoviesUseCase
import com.ygs.feature_movies_list.models.UIMovieSummary
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

    fun handleAction(action: MovieListActions): UInt {
        when (action) {
            MovieListActions.LoadMovies -> loadMovies()
            MovieListActions.RefreshMovies -> refreshMovies()
        }
        return UInt.MIN_VALUE
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.value = MovieListState.Loading
            try {
                _state.value = MovieListState.Success(fetchMoviesFromRepository())
            } catch (e: Exception) {
                _state.value = MovieListState.Error("Failed to load movies")
            }
        }

    }

    private fun refreshMovies() {
        _state.value = MovieListState.Refreshing
        viewModelScope.launch {
            try {
                _state.value = MovieListState.Success(fetchMoviesFromRepository())
            } catch (e: Exception) {
                _state.value = MovieListState.Error("Failed to refresh movies")
            }
        }
    }

    private suspend fun fetchMoviesFromRepository(): List<UIMovieSummary> {
        return getMoviesUseCase.getMovies().map { movie ->
            UIMovieSummary(
                id = movie.id,
                name = movie.name,
                price = movie.price
            )
        }
    }
}
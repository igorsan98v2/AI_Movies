package com.ygs.feature_movie_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ygs.domain.usecase.GetMovieDetailsUseCase
import com.ygs.feature_movie_details.models.UIMovieDetails
import com.ygs.feature_movie_details.mvi.MovieDetailsActions
import com.ygs.feature_movie_details.mvi.MovieDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Idle)
    val state: StateFlow<MovieDetailsState> = _state

    fun handleAction(action: MovieDetailsActions) {
        when (action) {
            is MovieDetailsActions.FetchMovieDetails -> fetchMovieDetails(action.movieId)
            is MovieDetailsActions.RefreshMovieDetails -> refreshMovieDetails(action.movieId)
        }
    }

    private fun fetchMovieDetails(movieId: String) {

        viewModelScope.launch {
            _state.value = MovieDetailsState.Loading
            try {
                val result = getMovieDetailsUseCase.getMovieDetails(movieId)
                _state.value = MovieDetailsState.Success(
                    UIMovieDetails(
                        imageUrl = result.imageUrl,
                        meta = result.meta,
                        name = result.name,
                        price = result.price,
                        rating = result.rating,
                        synopsis = result.synopsis
                    )
                )
            } catch (e: Exception) {
                _state.value = MovieDetailsState.Error("Failed to load movie details")
            }
        }


    }

    private fun refreshMovieDetails(movieId: String) {
        _state.value = MovieDetailsState.Refreshing
        viewModelScope.launch {
            try {
                val result = getMovieDetailsUseCase.getMovieDetails(movieId)
                _state.value = MovieDetailsState.Success(
                    UIMovieDetails(
                        imageUrl = result.imageUrl,
                        meta = result.meta,
                        name = result.name,
                        price = result.price,
                        rating = result.rating,
                        synopsis = result.synopsis
                    )
                )
            } catch (e: Exception) {
                _state.value = MovieDetailsState.Error("Failed to refresh movie details")
            }
        }
    }
}
package com.ygs.feature_movie_details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ygs.feature_movie_details.R
import com.ygs.feature_movie_details.mvi.MovieDetailsActions
import com.ygs.feature_movie_details.mvi.MovieDetailsState
import com.ygs.feature_movie_details.presentation.MovieDetailsViewModel
import com.ygs.feature_movie_details.ui.components.MovieDetailsContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: String,
    onBack: (() -> Unit)? = null,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    // Start loading movie details when the MovieDetailsScreen is displayed
    LaunchedEffect(movieId) {
        viewModel.handleAction(MovieDetailsActions.LoadMovieDetails(movieId))
    }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.movie_details)) },
                navigationIcon = {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(Icons.Default.ArrowBack, "back")
                    }
                }
            )
        },
        snackbarHost = {
            if (snackbarHostState.currentSnackbarData != null) {
                SnackbarHost(
                    hostState = snackbarHostState,
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Content for MovieDetailsScreen
            when (val currentState = state.value) {
                MovieDetailsState.Idle -> {}
                MovieDetailsState.Loading -> {
                    CircularProgressIndicator()
                }

                MovieDetailsState.Refreshing -> {}
                is MovieDetailsState.Success -> {
                    MovieDetailsContent(movie = currentState.movie)
                }

                is MovieDetailsState.Error -> {
                    // Collect error message
                    val errorMessage = currentState.message

                    // Show snackbar with error message
                    LaunchedEffect(errorMessage) {
                        snackbarHostState.showSnackbar(errorMessage)
                    }
                }
            }
        }
    }
}


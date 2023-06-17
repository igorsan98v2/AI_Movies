package com.ygs.feature_movies_list.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ygs.feature_movies_list.R
import com.ygs.feature_movies_list.models.UIMovieSummary
import com.ygs.feature_movies_list.mvi.MovieListActions
import com.ygs.feature_movies_list.mvi.MovieListState
import com.ygs.feature_movies_list.presentation.MovieListViewModel
import com.ygs.feature_movies_list.ui.components.MovieItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(
    onMovieClicked: (String) -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.handleAction(MovieListActions.LoadMovies)
    }

    val state = viewModel.state.collectAsState()
    val filteringOptions =
        rememberSaveable { mutableStateOf(listOf<(UIMovieSummary) -> Boolean>()) }
    val sortByPrice = rememberSaveable { mutableStateOf(false) }
    val sortByName = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.movies_list_screen_title)) },
                actions = {
                    // Add IconButton for sorting by price
                    IconButton(onClick = { sortByPrice.value = !sortByPrice.value }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_attach_money),
                            "Sort by price"
                        )
                    }

                    // Add IconButton for sorting by name
                    IconButton(onClick = { sortByName.value = !sortByName.value }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sort_by_alpha),
                            "Sort by name"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

    // Filtering and sorting options can be added here, below the Scaffold and above the movie list UI

        when (val currentState = state.value) {
            MovieListState.Idle, MovieListState.Loading, MovieListState.Refreshing -> {
                // Display loading indicator
            }

            is MovieListState.Success -> {
                val sortOrder = when {
                    sortByPrice.value && !sortByName.value -> Comparator.comparing(UIMovieSummary::price)
                    !sortByPrice.value && sortByName.value -> Comparator.comparing(UIMovieSummary::name)
                    sortByPrice.value && sortByName.value -> Comparator.comparing(UIMovieSummary::price).thenComparing(UIMovieSummary::name)
                    // Note: If no sorting options are selected, return the original order
                    else -> Comparator.comparingInt<UIMovieSummary> { 0 }
                }
                val sortedMovies = remember(sortByPrice.value, sortByName.value) {
                    currentState.movies
                        .filter { movie -> filteringOptions.value.all { filter -> filter(movie) } }
                        .sortedWith(sortOrder)
                }

                LazyColumn(contentPadding = innerPadding) {
                    itemsIndexed(sortedMovies) { index, movie ->
                        MovieItem(
                            movie = movie,
                            onMovieClicked = { onMovieClicked(movie.id) },
                            showAlternativeBackground = index % 2 == 0
                        )
                    }
                }
            }

            is MovieListState.Error -> {
                // Display error message
            }
        }
    }
}
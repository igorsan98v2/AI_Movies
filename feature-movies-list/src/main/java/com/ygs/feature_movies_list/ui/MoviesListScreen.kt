package com.ygs.feature_movies_list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ygs.feature_movies_list.R
import com.ygs.feature_movies_list.models.UIMovieSummary
import com.ygs.feature_movies_list.mvi.MovieListActions
import com.ygs.feature_movies_list.mvi.MovieListState
import com.ygs.feature_movies_list.presentation.MovieListViewModel
import com.ygs.feature_movies_list.ui.components.MovieItem


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

    val snackbarHostState = remember { SnackbarHostState() }



    Scaffold(
        snackbarHost = {
            if (snackbarHostState.currentSnackbarData != null) {
                SnackbarHost(
                    hostState = snackbarHostState,
                )
            }
        },
        topBar = {
            MoviesListTopBar(
                sortByPrice = sortByPrice.value,
                onSortByPriceChanged = { sortByPrice.value = it },
                sortByName = sortByName.value,
                onSortByNameChanged = { sortByName.value = it }
            )
        }
    ) { innerPadding ->

        when (val currentState = state.value) {
            MovieListState.Idle, MovieListState.Loading, MovieListState.Refreshing -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }

            is MovieListState.Success -> {
                val sortedMovies = getSortedMoviesList(
                    movies = currentState.movies, // Pass the movies list
                    sortByPrice = sortByPrice.value,
                    sortByName = sortByName.value,
                    filteringOptions = filteringOptions.value
                )
                MoviesList(
                    movies = sortedMovies,
                    onMovieClicked = onMovieClicked,
                    contentPadding = innerPadding
                )
            }

            is MovieListState.Error -> {
                val errorLabel = stringResource(id = R.string.error)
                LaunchedEffect(key1 = currentState.message) {
                    snackbarHostState.showSnackbar(
                        currentState.message,
                        actionLabel = errorLabel,
                        withDismissAction = true
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListTopBar(
    sortByPrice: Boolean,
    onSortByPriceChanged: (Boolean) -> Unit,
    sortByName: Boolean,
    onSortByNameChanged: (Boolean) -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.movies_list_screen_title)) },
        actions = {
            // Add IconButton for sorting by price
            IconButton(onClick = { onSortByPriceChanged(!sortByPrice) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_attach_money),
                    "Sort by price",
                    tint = if (sortByPrice) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
                )
            }

            // Add IconButton for sorting by name
            IconButton(onClick = { onSortByNameChanged(!sortByName) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort_by_alpha),
                    "Sort by name",
                    tint = if (sortByName) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

@Composable
fun MoviesList(
    movies: List<UIMovieSummary>,
    onMovieClicked: (String) -> Unit,
    contentPadding: PaddingValues
) {
    LazyColumn(contentPadding = contentPadding) {
        itemsIndexed(movies) { index, movie ->
            MovieItem(
                movie = movie,
                onMovieClicked = { onMovieClicked(movie.id) },
                showAlternativeBackground = index % 2 == 0
            )
        }
    }
}

@Composable
fun getSortedMoviesList(
    movies: List<UIMovieSummary>,
    sortByPrice: Boolean,
    sortByName: Boolean,
    filteringOptions: List<(UIMovieSummary) -> Boolean>
): List<UIMovieSummary> {
    return remember(sortByPrice, sortByName) {
        val sortOrder = when {
            sortByPrice && !sortByName -> Comparator.comparing(UIMovieSummary::price)
            !sortByPrice && sortByName -> Comparator.comparing(UIMovieSummary::name)
            sortByPrice && sortByName -> Comparator.comparing(UIMovieSummary::price)
                .thenComparing(UIMovieSummary::name)

            else -> Comparator.comparingInt { 0 }
        }

        movies
            .filter { movie -> filteringOptions.all { filter -> filter(movie) } }
            .sortedWith(sortOrder)
    }
}
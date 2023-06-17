package com.ygs.aimovies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ygs.feature_movie_details.ui.MovieDetailsScreen
import com.ygs.feature_movies_list.ui.MoviesListScreen

sealed class NavGraph(val route: String) {
    object MovieList : NavGraph("movieList")
    object MovieDetails : NavGraph("movieDetails/{movieId}") {
        fun createRoute(movieId: String) = "movieDetails/$movieId"
    }
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavGraph.MovieList.route) {
        composable(NavGraph.MovieList.route) {
            MoviesListScreen(onMovieClicked = { movieId ->
                navController.navigate(NavGraph.MovieDetails.createRoute(movieId))
            })
        }

        composable(NavGraph.MovieDetails.route) { entry ->
            val movieId = entry.arguments?.getString("movieId")
            if (movieId != null) {
                MovieDetailsScreen(movieId = movieId)
            }
        }
    }
}
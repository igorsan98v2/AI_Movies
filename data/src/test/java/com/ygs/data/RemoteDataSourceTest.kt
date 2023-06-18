package com.ygs.data

import com.ygs.data.remote.MoviesApiService
import com.ygs.data.remote.MoviesRemoteDataSource
import com.ygs.data.remote.models.MovieDetailsResponse
import com.ygs.data.remote.models.MovieItemResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class RemoteDataSourceTest {
    @Mock
    private lateinit var movieApiService: MoviesApiService
    private lateinit var remoteDataSource: MoviesRemoteDataSource

    @Before
    fun setUp() {
        // Initialize the mocks before running the tests
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testSuccessfulGetMoviesApiCall() = runBlocking {
        val expectedMovieList = listOf(
            MovieItemResponse(id = "1", name = "Movie 1", price = 12),
            MovieItemResponse(id = "2", name = "Movie 2", price = 32),
        )

        // Set up the behavior of the mocked API service
        `when`(movieApiService.getMovies()).thenReturn(expectedMovieList)

        // Initialize the RemoteDataSource and make the API call
        remoteDataSource = MoviesRemoteDataSource(movieApiService)
        val actualMovieList = remoteDataSource.getMovies()

        // Validate the API response
        assertEquals(expectedMovieList, actualMovieList)
    }

    @Test
    fun testSuccessfulGetMovieDetailsApiCall() = runBlocking {
        // Sample data to simulate a successful API response for movie details
        val movieId = "1"
        val expectedMovieDetail = MovieDetailsResponse(
            imageUrl = "https://example.com/movie1.jpg",
            meta = "Movie 1 Meta",
            name = "Movie 1",
            price = 10,
            rating = "4.5",
            synopsis = "Movie 1 Description"
        )

        // Set up the behavior of the mocked API service
        `when`(movieApiService.getMovieDetails(movieId)).thenReturn(expectedMovieDetail)

        // Initialize the RemoteDataSource and make the API call
        remoteDataSource = MoviesRemoteDataSource(movieApiService)
        val actualMovieDetail = remoteDataSource.getMovieDetails(movieId)

        // Validate the API response
        assertEquals(expectedMovieDetail, actualMovieDetail)
    }

    @Test(expected = ApiException::class)
    fun testFailedGetMoviesApiCall(): Unit = runBlocking {
        // Set up the behavior of the mocked API service to throw an exception
        `when`(movieApiService.getMovies()).thenThrow(ApiException("Network Error: Failed to get movies"))

        // Initialize the RemoteDataSource and make the API call
        remoteDataSource = MoviesRemoteDataSource(movieApiService)
        remoteDataSource.getMovies()

        // The test will pass if the expected IOException is thrown
    }

    @Test(expected = ApiException::class)
    fun testFailedGetMovieDetailsApiCall(): Unit = runBlocking {
        val movieId = "1"

        // Set up the behavior of the mocked API service to throw an exception
        `when`(movieApiService.getMovieDetails(movieId)).thenThrow(ApiException("Network Error: Failed to get movie details"))

        // Initialize the RemoteDataSource and make the API call
        remoteDataSource = MoviesRemoteDataSource(movieApiService)
        remoteDataSource.getMovieDetails(movieId)

        // The test will pass if the expected IOException is thrown
    }
}



// ApiException.kt
class ApiException(message: String) : RuntimeException(message)

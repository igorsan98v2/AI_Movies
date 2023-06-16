package com.ygs.domain.repository

import com.ygs.domain.usecase.GetMovieDetailsUseCase
import com.ygs.domain.usecase.GetMoviesUseCase

interface MoviesRepository :
    GetMoviesUseCase,
    GetMovieDetailsUseCase {}


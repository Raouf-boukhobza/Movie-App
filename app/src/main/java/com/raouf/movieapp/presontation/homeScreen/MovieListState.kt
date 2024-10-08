package com.raouf.movieapp.presontation.homeScreen

import com.raouf.movieapp.domain.model.Movie
import com.raouf.movieapp.domain.util.Category

data class MovieListState(
    val isLoading: Boolean = false,
    val currentCategory: Category = Category.popular,
    val popularMoviesPage: Int = 1,
    val upcomingMoviesPage: Int = 1,
    val popularMoviesList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
    val trendingMovie: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val topRatedMoviesPages: Int = 1,
)
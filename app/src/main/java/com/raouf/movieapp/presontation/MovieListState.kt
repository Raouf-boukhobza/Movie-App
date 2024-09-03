package com.raouf.movieapp.presontation

import com.raouf.movieapp.domain.model.Movie
import com.raouf.movieapp.domain.util.Category

data class MovieListState(
    val isLoading: Boolean = false,
    val currentCategory: Category = Category.popular,
    val popularMoviesPage: Int = 1,
    val upcomingMoviesPage: Int = 1,
    val popularMoviesList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList()
)
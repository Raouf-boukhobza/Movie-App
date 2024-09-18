package com.raouf.movieapp.presontation.upcoming

import com.raouf.movieapp.domain.model.Movie


data class UpcomingState(
    val upcomingMovieList: List<Movie> = emptyList(),
    val upcomingMoviesPage : Int = 1,
    val isLoading : Boolean = false
)
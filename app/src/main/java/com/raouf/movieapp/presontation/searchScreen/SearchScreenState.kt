package com.raouf.movieapp.presontation.searchScreen

import com.raouf.movieapp.domain.model.Movie

data class SearchScreenState(
    val searchMovies: List<Movie> = emptyList(),
    val query: String = "",
    val isLoading : Boolean = false,
    val searchMoviesPage : Int = 1
)
package com.raouf.movieapp.presontation.searchScreen

sealed interface SearchScreenEvent {
    data class GetSearchMovies(val query : String) : SearchScreenEvent
    data class AddQuery(val query: String) : SearchScreenEvent
}
package com.raouf.movieapp.presontation.homeScreen

sealed interface MoviesScreenUiEvent {
    data class  Paginate(val category: String) : MoviesScreenUiEvent
}
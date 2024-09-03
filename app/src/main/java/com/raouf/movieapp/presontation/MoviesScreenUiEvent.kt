package com.raouf.movieapp.presontation

import com.raouf.movieapp.domain.util.Category

sealed interface MoviesScreenUiEvent {
    data class  Paginate(val category: String) : MoviesScreenUiEvent
    data class Navigate(val category : Category ) : MoviesScreenUiEvent
}
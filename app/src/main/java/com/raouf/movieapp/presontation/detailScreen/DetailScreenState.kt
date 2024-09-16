package com.raouf.movieapp.presontation.detailScreen

import com.raouf.movieapp.domain.model.Movie

data class DetailScreenState(
    val selectedMovie : Movie? = null,
    val isLoading : Boolean = false,
    val playVideo : Boolean = false
)
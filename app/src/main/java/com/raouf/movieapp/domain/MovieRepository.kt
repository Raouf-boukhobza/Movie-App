package com.raouf.movieapp.domain

import com.raouf.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository  {

    suspend fun getMoviesData(
        forceFetchFromApi : Boolean,
        category: String,
        page : Int
    ):Flow<Resource<List<Movie>>>

    suspend fun getMovie(id :Int) : Flow<Resource<Movie>>
}
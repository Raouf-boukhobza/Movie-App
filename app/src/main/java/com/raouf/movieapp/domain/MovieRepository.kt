package com.raouf.movieapp.domain

import com.raouf.movieapp.domain.model.Movie
import com.raouf.movieapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository  {

    suspend fun getMoviesList(
        forceFetchFromApi : Boolean,
        category: String,
        page : Int
    ):Flow<Resource<List<Movie>>>

    suspend fun getMovie(id :Int ) : Flow<Resource<Movie>>

    suspend fun getTrendingMovie(
       category: String
    ) : Flow<Resource<List<Movie>>>

    suspend fun getTopRatedMovies (category: String , page: Int) : Flow<Resource<List<Movie>>>
}
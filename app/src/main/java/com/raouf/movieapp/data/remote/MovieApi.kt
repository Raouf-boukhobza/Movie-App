package com.raouf.movieapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("Movie/{category}")
    suspend fun getMovieData(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("Api_key") apiKey: String = ApiKey
    ) : MovieListDto

    companion object {
        const val baseUrl = "https://api.themoviedb.org/3/"
        const val image_BaseUrl = "https://image.tmdb.org/t/p/w500"
        const val ApiKey = "05bde11ed9ddaf41959f5df9e43fa6a8"
    }
}
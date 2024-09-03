package com.raouf.movieapp.data.remote

import com.raouf.movieapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = ApiKey
    ) : MovieListDto


    @GET("search/multi")
    suspend fun getSearchMovies(
        @Query("api_key") apiKey : String = ApiKey,
        @Query("page") page: Int,
        @Query("query") query : String
    ) : MovieListDto


    @GET("trending/{type}/{time}")
    suspend fun getTrendingMovies(
        @Path("type") type : String,
        @Path("time") time : String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = ApiKey
    ) : MovieListDto



    companion object {
        const val baseUrl = "https://api.themoviedb.org/3/"
        const val image_BaseUrl = "https://image.tmdb.org/t/p/w500"
        const val ApiKey = BuildConfig.api_key
    }
}
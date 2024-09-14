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
        @Query("api_key") apiKey: String = APIKEY
    ) : MovieListDto


    @GET("search/multi")
    suspend fun getSearchMovies(
        @Query("api_key") apiKey : String = APIKEY,
        @Query("page") page: Int,
        @Query("query") query : String
    ) : MovieListDto


    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = APIKEY
    ) : MovieListDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = APIKEY,
        @Query("page") page: Int
    ) : MovieListDto


    @GET("movie/{movie_id}")
    suspend fun getMovieByIdFromRemote (
        @Path("movie_id") movieId : Int,
        @Query("api_key") apiKey: String = APIKEY,
    ):MovieDto


    @GET("movie/{movie_id}/videos")
    suspend fun getVideo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = APIKEY,
    ) : VideoDto

    companion object {
        const val baseUrl = "https://api.themoviedb.org/3/"
        const val image_BaseUrl = "https://image.tmdb.org/t/p/w500"
        const val APIKEY = BuildConfig.API_KEY
    }

}
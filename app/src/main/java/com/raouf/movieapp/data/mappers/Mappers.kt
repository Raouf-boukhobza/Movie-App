package com.raouf.movieapp.data.mappers

import com.raouf.movieapp.data.local.MovieEntity
import com.raouf.movieapp.data.remote.MovieDto
import com.raouf.movieapp.domain.model.Movie


fun MovieDto.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        category = category,
        originalLanguage = originalLanguage ?: "",
        originalName = originalName ?: "",
        originCountry = originCountry ?: emptyList(),
        overview = overview ?: "",
        backdropPath = backdropPath ?: "",
        firstAirDate = firstAirDate ?: "",
        name = name ?: "",
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: "",
        voteCount = voteCount ?: 0,
        voteAverage = voteAverage ?: 0.0,
        genreIds = try {
            genreIds?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        }
    )
}


fun MovieEntity.toMovie(
    category: String
):Movie{
    return Movie(
        id = id,
        adult = adult,
        category = category,
        originalLanguage = originalLanguage ,
        originalName = originalName,
        originCountry = originCountry ,
        overview = overview ,
        backdropPath = backdropPath ,
        firstAirDate = firstAirDate ,
        name = name ,
        popularity = popularity ,
        posterPath = posterPath ,
        voteCount = voteCount ,
        voteAverage = voteAverage ,
        genreIds = try {
            genreIds.split(",") .map { it.toInt() }
        }catch (e : Exception){
            listOf(-1,-2)
        }
    )
}
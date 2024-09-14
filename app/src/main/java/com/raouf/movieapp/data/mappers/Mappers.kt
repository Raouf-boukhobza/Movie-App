package com.raouf.movieapp.data.mappers

import com.raouf.movieapp.data.local.MovieEntity
import com.raouf.movieapp.data.remote.Genres
import com.raouf.movieapp.data.remote.MovieDto
import com.raouf.movieapp.domain.model.Movie


fun MovieDto.toMovieEntity(
    category: String?
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        category = category ?: "",
        originalLanguage = originalLanguage ?: "",
        originalName = originalName ?: "",
        overview = overview ?: "",
        backdropPath = backdropPath ?: "",
        firstAirDate = firstAirDate ?: "",
        name = title ?: "",
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: "",
        voteCount = voteCount ?: 0,
        voteAverage = voteAverage ?: 0.0,
        genreIds = genres ?: listOf(Genres(0,"")) ,
        releaseDate = releaseDate ?: "",
        remoteId = id ?: 0,
        videoUrl = videoUrl ?: ""
    )
}


fun MovieEntity.toMovie(
):Movie{
    return Movie(
        id = id,
        adult = adult,
        category = category,
        originalLanguage = originalLanguage ,
        originalName = originalName,
        overview = overview ,
        backdropPath = backdropPath ,
        firstAirDate = firstAirDate ,
        name = name ,
        popularity = popularity ,
        posterPath = posterPath ,
        voteCount = voteCount ,
        voteAverage = voteAverage ,
        genres = genreIds,
        releasDate = releaseDate,
        remoteId = remoteId,
        videoUrl = videoUrl
    )
}
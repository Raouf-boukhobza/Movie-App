package com.raouf.movieapp.domain.model

import com.raouf.movieapp.data.remote.Genres

data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genres: List<Genres>,
    val name: String,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val category: String,
    val id: Int,
    val releasDate: String,
    val remoteId: Int
)

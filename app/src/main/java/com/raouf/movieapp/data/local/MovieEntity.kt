package com.raouf.movieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MovieEntity(
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: String,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val category : String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
package com.raouf.movieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.raouf.movieapp.data.remote.Genres


@Entity
data class MovieEntity(
    val adult: Boolean,
    val backdropPath: String,
    val releaseDate : String,
    val firstAirDate: String,
    @TypeConverters(GenresConverter::class)
    val genreIds: List<Genres>,
    val name: String,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val category : String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteId : Int
)
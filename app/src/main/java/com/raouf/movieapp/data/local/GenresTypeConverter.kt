package com.raouf.movieapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raouf.movieapp.data.remote.Genres

class GenresConverter {

    @TypeConverter
    fun fromGenresList(genres: List<Genres>): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenresList(genresString: String): List<Genres> {
        val listType = object : TypeToken<List<Genres>>() {}.type
        return Gson().fromJson(genresString, listType)
    }
}
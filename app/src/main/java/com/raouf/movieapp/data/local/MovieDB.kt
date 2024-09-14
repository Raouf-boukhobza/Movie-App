package com.raouf.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenresConverter::class)
abstract class MovieDB : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}
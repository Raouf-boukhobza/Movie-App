package com.raouf.movieapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert



@Dao
interface MovieDao  {

    @Upsert
    suspend fun UpsertMovieList(movies : List<MovieEntity>)

    @Query("SELECT * FROM MOVIEENTITY WHERE id = :id")
    fun getMovieById ( id : Int) :MovieEntity

    @Query("SELECT * FROM MOVIEENTITY WHERE category = :category")
    fun getMovieByCategory ( category : String) :List<MovieEntity>
}
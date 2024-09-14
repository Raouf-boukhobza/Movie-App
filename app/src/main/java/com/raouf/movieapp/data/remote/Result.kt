package com.raouf.movieapp.data.remote

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val site: String,
    val size: Int,
    val type: String
)
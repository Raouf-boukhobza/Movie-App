package com.raouf.movieapp.domain.util

sealed class Resource<T>(
    val data : T? = null,
    val message : String? =null
) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(data: T? = null , message: String): Resource<T>(message = message)
    class IsLoading<T>(val isLoading : Boolean) : Resource<T>(null)
}
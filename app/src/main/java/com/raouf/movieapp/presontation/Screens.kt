package com.raouf.movieapp.presontation

sealed class Screens(val route : String){

    data object Main : Screens(route = "main")
    data object Detail : Screens(route = "Detail")
}
package com.raouf.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.raouf.movieapp.presontation.MovieListViewModel
import com.raouf.movieapp.presontation.homeScreen.HomeNavGraph
import com.raouf.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val movieViewModel = hiltViewModel<MovieListViewModel>()
                val movieList = movieViewModel.movieListState.collectAsState().value.popularMoviesList
                 HomeNavGraph()
            }
        }
    }
}

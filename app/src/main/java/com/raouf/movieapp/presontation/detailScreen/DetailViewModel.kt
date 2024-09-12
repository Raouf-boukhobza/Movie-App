package com.raouf.movieapp.presontation.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raouf.movieapp.domain.MovieRepository
import com.raouf.movieapp.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _selectedMovie: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            _selectedMovie.update {
                repository.getMovie(id).data
            }
        }
    }

}
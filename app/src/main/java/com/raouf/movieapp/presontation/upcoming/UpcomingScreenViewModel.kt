package com.raouf.movieapp.presontation.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raouf.movieapp.domain.MovieRepository
import com.raouf.movieapp.domain.util.Category
import com.raouf.movieapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpcomingScreenViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _upcomingState = MutableStateFlow(UpcomingState())
    val upcomingState = _upcomingState.asStateFlow()

    init {
        getUpcomingMovies(false)
    }


    private fun getUpcomingMovies(forceFetchFromApi: Boolean) {
        viewModelScope.launch {
            _upcomingState.update {
                it.copy(
                    isLoading = true
                )
            }
            repository.getMoviesList(
                category = Category.popular.name,
                page = upcomingState.value.upcomingMoviesPage,
                forceFetchFromApi = forceFetchFromApi
            ).collectLatest { result ->
                when (result) {

                    is Resource.Error -> {
                        _upcomingState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Resource.IsLoading -> {
                        _upcomingState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { newMovies ->
                            _upcomingState.update {
                                it.copy(
                                   upcomingMovieList = upcomingState.value.upcomingMovieList + newMovies.shuffled(),
                                   upcomingMoviesPage = upcomingState.value.upcomingMoviesPage + 1
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    fun paginate(){
        getUpcomingMovies(true)
    }
}
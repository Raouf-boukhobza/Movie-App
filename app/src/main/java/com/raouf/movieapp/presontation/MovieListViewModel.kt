package com.raouf.movieapp.presontation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovies(false)
        getUpcomingMovies(false)
        getTrendingMovies()
        getTopRatedMovies()
    }

    fun onEvent(event: MoviesScreenUiEvent) {
        when (event) {
            is MoviesScreenUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        currentCategory = event.category
                    )
                }
            }

            is MoviesScreenUiEvent.Paginate -> {
                if (event.category == Category.popular.name) {
                    getPopularMovies(true)
                } else if (event.category == Category.upcoming.name) {
                    getUpcomingMovies(true)
                }
            }
        }
    }


    private fun getPopularMovies(forceFetchFromApi: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(
                    isLoading = true
                )
            }
            movieRepository.getMoviesList(
                category = Category.popular.name,
                page = movieListState.value.popularMoviesPage,
                forceFetchFromApi = forceFetchFromApi
            ).collectLatest { result ->
                when (result) {

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Resource.IsLoading -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { newMovies ->
                            _movieListState.update {
                                it.copy(
                                    popularMoviesList = movieListState.value.popularMoviesList + newMovies.shuffled(),
                                    popularMoviesPage = movieListState.value.popularMoviesPage + 1
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    private fun getTrendingMovies(){
        viewModelScope.launch {
            movieRepository.getTrendingMovie(Category.trenidng.name).collectLatest{result ->
                when(result){
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Resource.IsLoading -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let {movies ->
                            _movieListState.update {
                                it.copy(
                                 trendingMovie = movies
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    private fun getTopRatedMovies(){
        viewModelScope.launch {
            movieRepository.getTopRatedMovies(
                page = movieListState.value.topRatedMoviesPages ,
                category = Category.topRated.name
            ).collectLatest{ result ->
                when(result){
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Resource.IsLoading -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let {movies ->
                            _movieListState.update {
                                it.copy(
                                   topRatedMovies = movies,
                                    topRatedMoviesPages = movieListState.value.topRatedMoviesPages + 1
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    private fun getUpcomingMovies(forceFetchFromApi: Boolean) {
            viewModelScope.launch {
                _movieListState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                movieRepository.getMoviesList(
                    category = Category.upcoming.name,
                    page = movieListState.value.upcomingMoviesPage,
                    forceFetchFromApi = forceFetchFromApi
                ).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _movieListState.update {
                                it.copy(
                                    isLoading = false
                                )
                            }
                        }
                        is Resource.IsLoading -> {
                            _movieListState.update {
                                it.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                        is Resource.Success -> {
                            result.data?.let { newMovies ->
                                _movieListState.update {
                                    it.copy(
                                        upcomingMovieList = movieListState.value.upcomingMovieList + newMovies.shuffled(),
                                        upcomingMoviesPage = movieListState.value.upcomingMoviesPage + 1
                                    )
                                }
                            }

                        }
                    }
                }
            }

        }

    }

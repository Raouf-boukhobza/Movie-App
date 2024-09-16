package com.raouf.movieapp.presontation.searchScreen

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raouf.movieapp.domain.MovieRepository
import com.raouf.movieapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.GetSearchMovies -> {
                viewModelScope.launch {
                    repository.getSearchMovie(
                        event.query,
                        page = state.value.searchMoviesPage
                    ).collectLatest { result ->
                        when (result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false
                                    )
                                }
                            }

                            is Resource.IsLoading -> {
                                _state.update {
                                    it.copy(
                                        isLoading = result.isLoading
                                    )
                                }
                            }

                            is Resource.Success -> {
                                result.data?.let { movieList ->
                                    _state.update {
                                        it.copy(
                                            searchMovies = movieList,
                                            //searchMoviesPage = state.value.searchMoviesPage + 1
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }

            is SearchScreenEvent.AddQuery -> {
                _state.update {
                    it.copy(
                        query = event.query
                    )
                }
            }
        }
    }

}
package com.raouf.movieapp.presontation.detailScreen

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
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {


    private val _detailState: MutableStateFlow<DetailScreenState> = MutableStateFlow(DetailScreenState())
    val detailState = _detailState.asStateFlow()



    fun onEvent(event: DetailScreenEvent){
        when(event){
            DetailScreenEvent.PlayVideo -> {
                _detailState.update {
                    it.copy(
                        playVideo = true
                    )
                }
            }
        }
    }
    fun getMovieDetail(
        id : Int
    ){
        viewModelScope.launch {
         repository.getMovie(id).collectLatest { result ->
            when(result){
                is Resource.Error -> {
                    _detailState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
                is Resource.IsLoading -> {
                    _detailState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
                is Resource.Success -> {
                    _detailState.update {
                        it.copy(
                            selectedMovie = result.data
                        )
                    }
                }
            }
         }

        }
    }



}
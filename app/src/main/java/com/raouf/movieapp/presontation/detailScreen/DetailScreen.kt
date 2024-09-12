package com.raouf.movieapp.presontation.detailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RunningWithErrors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.raouf.movieapp.data.remote.MovieApi


@Composable
fun DetailScreen(
   id : Int
) {

    val detailViewModel : DetailViewModel = hiltViewModel()
    detailViewModel.getMovieById(id)
    val movie = detailViewModel.selectedMovie.collectAsState().value
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val backDropImageState = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(MovieApi.image_BaseUrl + movie?.backdropPath)
                .size(Size.ORIGINAL)
                .build()
        ).state
        if (backDropImageState is AsyncImagePainter.State.Success){
            Image(
                painter = backDropImageState.painter,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp)
            )
        }
        if (backDropImageState is AsyncImagePainter.State.Error){
            Icon(
                imageVector = Icons.Default.RunningWithErrors,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }

    }

}
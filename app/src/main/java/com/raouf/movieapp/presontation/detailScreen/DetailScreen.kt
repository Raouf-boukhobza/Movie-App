package com.raouf.movieapp.presontation.detailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.RunningWithErrors
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner

import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.raouf.movieapp.data.remote.MovieApi
import com.raouf.movieapp.domain.util.RatingBar
import java.util.Locale


@Composable
fun DetailScreen(
    id: Int
) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    detailViewModel.getMovieDetail(id)
    val state = detailViewModel.detailState.collectAsState().value
    val movie = state.selectedMovie
    movie?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .padding(top = 36.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(405.dp)
            ) {
                val backDropImageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(MovieApi.image_BaseUrl + movie.backdropPath)
                        .size(Size.ORIGINAL)
                        .build()
                ).state
                val posterImageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(MovieApi.image_BaseUrl + movie.posterPath)
                        .size(Size.ORIGINAL)
                        .build()
                ).state

                TrailerPlayer(
                    videoKey = movie.videoUrl,
                    lifecycleOwner = LocalLifecycleOwner.current
                )
                if (backDropImageState is AsyncImagePainter.State.Error) {
                    Icon(
                        imageVector = Icons.Default.RunningWithErrors,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Card(
                    ) {
                        if (posterImageState is AsyncImagePainter.State.Success) {
                            Image(
                                painter = posterImageState.painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))
                        Text(
                            text = movie.name,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,

                            )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            RatingBar(
                                rating = movie.voteAverage / 2,
                                stars = 5,
                            )
                            Text(
                                text = String.format(Locale.US, "%.2f", movie.voteAverage),
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }


                        Text(
                            text = movie.genres[0].name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.LightGray,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = movie.releasDate,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                        )


                    }
                }


            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Overview",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 12.dp)
            )
            Text(
                text = movie.overview,
                fontSize = 16.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )


        }

    }


}
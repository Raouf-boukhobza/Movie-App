package com.raouf.movieapp.presontation.upcoming

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.raouf.movieapp.data.remote.MovieApi
import com.raouf.movieapp.domain.model.Movie
import com.raouf.movieapp.domain.util.RatingBar
import java.util.Locale


@Composable
fun UpcomingScreen(
    state: UpcomingState
) {


    if (!state.isLoading) {
        val movieList = state.upcomingMovieList
        LazyColumn {
            items(movieList.size, key = { it }) { index ->
                UpcomingCard(
                    movie = movieList[index],
                )
            }

        }
    }
}


@Composable
fun UpcomingCard(
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.image_BaseUrl + movie.posterPath)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .background(
                color = Color.DarkGray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(18.dp)
            )
            .fillMaxWidth()
    ) {
        Card {
            if (posterImageState is AsyncImagePainter.State.Success) {
                Image(
                    painter = posterImageState.painter,
                    contentDescription = null,
                    modifier = Modifier
                        .width(125.dp)
                        .height(175.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {
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
                text = movie.releasDate,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
            )
        }
    }

}
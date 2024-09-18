@file:Suppress("DEPRECATION")

package com.raouf.movieapp.presontation.detailScreen

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    id: Int,
    state: DetailScreenState,
    onEvent: (DetailScreenEvent) -> Unit
) {
    onEvent(DetailScreenEvent.SelectMovie(id))
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
                val posterImageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(MovieApi.image_BaseUrl + movie.posterPath)
                        .size(Size.ORIGINAL)
                        .build()
                ).state
                Box(
                    modifier = Modifier.background(color = Color.Black)
                ) {
                    TrailerPlayer(
                        videoKey = movie.videoUrl,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Card {
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
                        Spacer(modifier = Modifier.height(30.dp))
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
            ExpandableText(
                text = movie.overview,
                collapsedMaxLines = 3
            )
        }

    }


}

@Composable
fun ExpandableText(text: String, collapsedMaxLines: Int = 3) {

    var isExpanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(8.dp)){
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            color = Color.LightGray,
            fontSize = 17.sp,
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .animateContentSize() // This will animate the expansion and collapse
        )

       // Show a button for expanding/collapsing

            Text(
                if (isExpanded) "Show Less" else "Show More",
                color = Color.Red,
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
            )
    }
}
package com.raouf.movieapp.presontation.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.raouf.movieapp.data.remote.MovieApi
import com.raouf.movieapp.presontation.MovieListViewModel
import kotlin.math.absoluteValue


@Composable
fun HomeScreen() {
    val viewModel: MovieListViewModel = hiltViewModel()
    val state = viewModel.movieListState.collectAsState().value
    val movieList = state.trendingMovie.shuffled().take(7)
    if (movieList.isNotEmpty()) {
        val pageState = rememberPagerState(
            pageCount = { movieList.size },
            initialPage = movieList.size  / 2
        )
        LazyColumn(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)){
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalPager(
                        state = pageState,
                        modifier = Modifier.fillMaxWidth(), // Ensure the pager takes up full width
                        contentPadding = PaddingValues(horizontal = 48.dp), // Add padding to show previews
                        pageSpacing = (-10).dp // Space between pages
                    ) { imageIndex ->
                        val imageState = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(MovieApi.image_BaseUrl + movieList[imageIndex].backdropPath)
                                .size(Size.ORIGINAL)
                                .build()
                        ).state

                        if (imageState is AsyncImagePainter.State.Success) {
                            Image(
                                modifier = Modifier

                                    .size(height = 200.dp, width = 300.dp)
                                    .graphicsLayer {
                                        clip = true
                                        // Scale the current page more for emphasis
                                        val pageOffset =
                                            (pageState.currentPage - imageIndex + pageState.currentPageOffsetFraction).absoluteValue
                                        lerp(
                                            start = 0.85f,
                                            stop = 1f,
                                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                        ).also { scale ->
                                            scaleX = scale
                                            scaleY = scale
                                        }
                                    }
                                    .clip(RoundedCornerShape(24.dp))
                                   ,
                                painter = imageState.painter,
                                contentDescription = null,
                            )


                        }


                    }
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Generate dots for each page
                        repeat(movieList.size) { index ->
                            // Change color based on whether it's the current page or not
                            val color = if (pageState.currentPage == index) Color.Black else Color.Gray

                            // Display each dot
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(color = color, shape = CircleShape)
                            )
                        }
                    }
                }
            }

        }


    }
}




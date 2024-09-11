package com.raouf.movieapp.presontation.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.raouf.movieapp.data.remote.MovieApi
import com.raouf.movieapp.domain.model.Movie
import com.raouf.movieapp.presontation.MovieListViewModel
import com.raouf.movieapp.ui.theme.lightBlack
import kotlin.math.absoluteValue


@Composable
fun HomeScreen(viewModel: MovieListViewModel) {

    val state = viewModel.movieListState.collectAsState().value
    val movieList = state.trendingMovie.shuffled().take(7)
    if (!state.isLoading) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                MoviePager(movieList)
            }
            item {
                ListWithTypes(
                   movieList = state.topRatedMovies,
                    type = "Top Rated"
                )
            }
        }
    }
}

@Composable
fun MoviePager(movieList: List<Movie>) {
    val pageState = rememberPagerState(
        pageCount = { movieList.size },
        initialPage = movieList.size / 2
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = Color.Black
            )
            .padding(vertical = 12.dp)
    ) {
        HorizontalPager(
            state = pageState,
            modifier = Modifier.fillMaxWidth(), // Ensure the pager takes up full width
            contentPadding = PaddingValues(horizontal = 48.dp), // Add padding to show previews
            pageSpacing = (-16).dp // Space between pages
        ) { Index ->
            val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(MovieApi.image_BaseUrl + movieList[Index].backdropPath)
                    .size(Size.ORIGINAL)
                    .build()
            ).state

            if (imageState is AsyncImagePainter.State.Success) {
                Box {
                    Image(
                        painter = imageState.painter,
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer {
                                clip = true
                                shape = RoundedCornerShape(18.dp)
                                // Scale the current page more for emphasis
                                val pageOffset =
                                    (pageState.currentPage - Index + pageState.currentPageOffsetFraction).absoluteValue
                                lerp(
                                    start = 0.8f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }
                            }
                            .size(height = 160.dp, width = 300.dp)
                    )
                    if (pageState.currentPage == Index) {

                        Text(
                            text = movieList[Index].name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.75f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(6.dp)
                        )

                    }
                }


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
                val color =
                    if (pageState.currentPage == index) Color.White
                    else Color.Gray

                // Display each dot
                Box(
                    modifier = Modifier
                        .size(
                            if (pageState.currentPage == index) 6.dp
                            else 4.dp
                        )
                        .background(color = color, shape = CircleShape)
                )
            }
        }
    }
}


@Composable
fun ListWithTypes(
    type: String,
    movieList: List<Movie>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth() ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text = type,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                text = "See all",
                fontSize = 16.sp,
                color = Color.Red,
                fontWeight = FontWeight.Medium,
            )
        }

        LazyRow(modifier = Modifier.fillMaxWidth() ,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(movieList) { movie ->
                MovieCard(movie)
            }
        }
    }
}


@Composable
fun MovieCard(movie: Movie) {
    Column {
        Card(

            modifier = Modifier.clip(RoundedCornerShape(18.dp))
                .border(width = 3.dp , color = lightBlack)
        ){
            val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(MovieApi.image_BaseUrl + movie.posterPath)
                    .size(Size.ORIGINAL)
                    .build()
            ).state
            if (imageState is AsyncImagePainter.State.Success) {
                Image(
                    painter = imageState.painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(height = 185.dp, width = 125.dp)
                )
            }

        }
    }
}




package com.raouf.movieapp.presontation.searchScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raouf.movieapp.presontation.homeScreen.MovieCard

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchScreenState,
    onEvent: (SearchScreenEvent) -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    onEvent(SearchScreenEvent.GetSearchMovies(state.query))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
    ) {
        SearchTextField(
            state = state,
            onEvent = onEvent
        )

        val movieList = state.searchMovies
        Spacer(modifier = Modifier.height(8.dp))
        if (movieList.isEmpty()) {
            Text(
                text = "No Result",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = 250.dp),
                color = Color.White
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize()
            ) {
                items(movieList.size, key = { it }) { index ->
                    MovieCard(
                        movie =  movieList[index],
                        height = 170.dp,
                        with = 120.dp,
                        navigateToDetails
                    )
                }
            }
        }

    }
}

@Composable
fun SearchTextField(
    state: SearchScreenState,
    onEvent: (SearchScreenEvent) -> Unit
) {
    OutlinedTextField(
        value = state.query,
        onValueChange = { query ->
            onEvent(SearchScreenEvent.AddQuery(query = query))
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = Color.LightGray
            )
        },
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Red,
            unfocusedBorderColor = Color.LightGray,
            focusedTextColor = Color.LightGray,
            unfocusedTextColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = {
            Text(
                text = "Search",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    )
}

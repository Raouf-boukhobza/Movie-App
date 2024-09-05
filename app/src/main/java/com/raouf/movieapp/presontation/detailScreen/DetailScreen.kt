package com.raouf.movieapp.presontation.detailScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun DetailScreen(modifier: Modifier = Modifier , navController: NavHostController) {
    Text("Detail" , fontSize = 32.sp)
}
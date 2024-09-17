package com.raouf.movieapp.presontation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.raouf.movieapp.presontation.detailScreen.DetailScreen
import com.raouf.movieapp.presontation.detailScreen.DetailViewModel

@Composable
fun MainNavHost(mainNavController: NavHostController) {

    NavHost(
        navController = mainNavController,
        startDestination = Screens.Main.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Screens.Main.route) {
            HomeNavGraph(
                navigateToDetail = { id ->
                    mainNavController.navigate(
                        route = "${Screens.Detail.route}/$id"
                    )
                }
            )
        }
        composable(
            route = "${Screens.Detail.route}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )) {
            val id = it.arguments?.getInt("id")!!
            val detailViewModel: DetailViewModel = hiltViewModel()
            val state = detailViewModel.detailState.collectAsState().value
            DetailScreen(
                id = id,
                state = state,
                onEvent = detailViewModel::onEvent
            )
        }
    }

}
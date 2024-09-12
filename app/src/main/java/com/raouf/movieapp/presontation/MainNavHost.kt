package com.raouf.movieapp.presontation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.raouf.movieapp.presontation.detailScreen.DetailScreen
import com.raouf.movieapp.presontation.homeScreen.HomeNavGraph

@Composable
fun MainNavHost(mainNavController: NavHostController) {

    NavHost(
        navController = mainNavController,
        startDestination = Screens.Main.route
    ) {
        composable(route =  Screens.Main.route) {
            HomeNavGraph(
                navigateToDetail = {id ->
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
            DetailScreen(
              id = id
            )
        }
    }

}
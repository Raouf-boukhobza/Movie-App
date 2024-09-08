package com.raouf.movieapp.presontation.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = { BottomBar(navController) },
        topBar = {
         TopBar()
        }
        ) { padding ->

        NavHost(
            navController = navController,
            startDestination = BottomBarScreens.Home.root,
            modifier = Modifier.padding(padding)
        ) {
            composable(route = BottomBarScreens.Home.root) {
                HomeScreen()
            }
            composable(route = BottomBarScreens.Search.root) {
                SearchScreen()
            }
            composable(route = BottomBarScreens.Upcoming.root) {
                UpcomingScreen()
            }
            composable(route = BottomBarScreens.Profile.root) {
                Text("profile")
            }

        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreens.Home,
        BottomBarScreens.Search,
        BottomBarScreens.Upcoming,
        BottomBarScreens.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.root == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier.padding(8.dp)

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color.Black.copy(alpha = 0.6f))
                    .padding(horizontal = 8.dp)
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }

    }
}

@Composable
fun AddItem(
    screen: BottomBarScreens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any {
        it.route == screen.root
    } == true
    val interactionSource = remember { MutableInteractionSource() }


    Row(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(screen.root) {
                    popUpTo(
                        navController.graph.findStartDestination().id
                    )
                    launchSingleTop = true
                }
            }
            .background(
                color = if (selected) Color.Red else Color.Transparent,
                shape = CircleShape
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (selected) screen.icon
            else screen.unFocusIcon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = if (selected) screen.title
            else "",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
   TopAppBar(
       title = {
           Text(
               text = buildAnnotatedString {
                   append("Film")
                   withStyle(
                   style = SpanStyle(
                       color = Color.Red,
                       fontFamily = FontFamily.Cursive,
                       fontSize = 34.sp
                   )
                   ){
                       append("Flow")
                   }
               },
               fontSize = 32.sp,
               fontWeight = FontWeight.Bold
           )
       }
   )
}



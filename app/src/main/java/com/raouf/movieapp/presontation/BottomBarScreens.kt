package com.raouf.movieapp.presontation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Upcoming
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreens(
    val root: String,
    val icon: ImageVector,
    val title: String,
    val unFocusIcon : ImageVector
) {

    data object Home : BottomBarScreens(
        root = "some",
        icon = Icons.Filled.Home,
        title = "Home",
        unFocusIcon = Icons.Outlined.Home
    )
    data object Search : BottomBarScreens(
        root = "search",
        icon = Icons.Filled.Search,
        title = "Search",
        unFocusIcon = Icons.Outlined.Search
    )
    data object Upcoming: BottomBarScreens(
        root = "upcoming",
        icon = Icons.Filled.Upcoming,
        title = "UpComing",
        unFocusIcon = Icons.Outlined.Upcoming
    )

    data object Profile : BottomBarScreens(
        root = "profile",
        icon = Icons.Filled.Person,
        title = "Profile",
        unFocusIcon = Icons.Outlined.Person
    )
}





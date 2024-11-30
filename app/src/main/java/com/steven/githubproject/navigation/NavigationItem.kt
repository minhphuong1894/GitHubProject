package com.steven.githubproject.navigation

sealed class NavigationItem(val route : String){

    data object UserScreen : NavigationItem("users_screen")
    data object UserDetailsScreen : NavigationItem("user_details_screen/{loginName}") {
        fun createRoute(loginName: String) = "user_details_screen/$loginName"
    }

}
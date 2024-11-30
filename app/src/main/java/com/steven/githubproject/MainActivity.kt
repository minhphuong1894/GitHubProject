package com.steven.githubproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.steven.githubproject.navigation.NavigationItem
import com.steven.githubproject.screen.userdetails.UserDetailsScreen
import com.steven.githubproject.screen.users.UserScreen
import com.steven.githubproject.ui.theme.GithubProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavigationItem.UserScreen.route
                    ) {

                        // GitHub Users Screen
                        composable(route = NavigationItem.UserScreen.route) {
                            UserScreen(navController)
                        }

                        // GitHub User Details
                        composable(
                            route = NavigationItem.UserDetailsScreen.route,
                        ) {
                            UserDetailsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

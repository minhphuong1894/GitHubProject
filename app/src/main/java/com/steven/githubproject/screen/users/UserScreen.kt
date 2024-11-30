package com.steven.githubproject.screen.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.steven.githubproject.components.UserCard
import com.steven.githubproject.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavController, userViewModel: UserViewModel = hiltViewModel()) {

    val users = userViewModel.users.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Github Users",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.Transparent)
        ) {

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(users.itemCount) { item ->
                    users[item]?.let {
                        UserCard(
                            user = it,
                            onClick = {
                                navController.navigate(
                                    NavigationItem.UserDetailsScreen.createRoute(it.login)
                                )
                            },
                            avatarUrl = it.avatarUrl,
                            login = it.login,
                            htmlUrl = it.htmlUrl
                        )
                    }
                }
            }
        }
    }
}

package com.steven.githubproject.screen.userdetails

import com.steven.domain.models.GitHubUserDetails

data class UserDetailsState(
    val isLoading: Boolean = false,
    val userDetails: GitHubUserDetails? = null,
    val error: String = ""
)
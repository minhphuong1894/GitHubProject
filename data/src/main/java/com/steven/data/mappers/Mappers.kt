package com.steven.data.mappers

import com.steven.data.models.GitHubUserDTO
import com.steven.data.models.GitHubUserDetailsDTO
import com.steven.domain.models.GitHubUser
import com.steven.domain.models.GitHubUserDetails

fun GitHubUserDTO.toDomain(): GitHubUser {
    return GitHubUser(
        id = id,
        login = login,
        avatarUrl = avatar_url,
        htmlUrl = html_url
    )
}

fun GitHubUserDetailsDTO.toDomain(): GitHubUserDetails {
    return GitHubUserDetails(
        login = login,
        avatarUrl = avatar_url,
        htmlUrl = html_url,
        location = location ?: "Unknown Location",
        followers = followers,
        following = following
    )
}
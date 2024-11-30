package com.steven.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GitHubUserDetails(
    @PrimaryKey(autoGenerate = false)
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String? = "Unknown Location",
    val followers: Int,
    val following: Int
)
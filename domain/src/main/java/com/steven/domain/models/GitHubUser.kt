package com.steven.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GitHubUser (
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String
)
package com.steven.data.network

import com.steven.data.models.GitHubUserDTO
import com.steven.data.models.GitHubUserDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("users")
    suspend fun getGitHubUsers(
        @Query("per_page") perPage: Int,
        @Query("since") since: Int
    ): List<GitHubUserDTO>

    @GET("users/{login_username}")
    suspend fun getGitHubUserDetails(
        @Path("login_username") loginName: String
    ): GitHubUserDetailsDTO

}
package com.steven.domain.repositories

import androidx.paging.PagingData
import com.steven.common.Resource
import com.steven.domain.models.GitHubUser
import com.steven.domain.models.GitHubUserDetails
import kotlinx.coroutines.flow.Flow

interface GitHubUserRepository {

    fun getGithubUsers(): Flow<PagingData<GitHubUser>>

    fun getGithubUserDetails(loginName: String): Flow<Resource<GitHubUserDetails>>
}
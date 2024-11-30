package com.steven.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.steven.common.NetworkBoundResource
import com.steven.common.Resource
import com.steven.data.mappers.toDomain
import com.steven.data.models.GitHubUserDetailsDTO
import com.steven.data.network.GitHubService
import com.steven.data.paging.GitHubUserRemoteMediator
import com.steven.data.room.GitHubUsersDatabase
import com.steven.domain.models.GitHubUser
import com.steven.domain.models.GitHubUserDetails
import com.steven.domain.repositories.GitHubUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitHubUserRepositoryImpl @Inject constructor(
    private val gitHubService: GitHubService,
    private val gitHubUsersDatabase: GitHubUsersDatabase,
) : GitHubUserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getGithubUsers(): Flow<PagingData<GitHubUser>> {
        return Pager(
            config = PagingConfig(
                20,
                enablePlaceholders = false
            ),
            remoteMediator = GitHubUserRemoteMediator(
                gitHubUsersDatabase, gitHubService
            )
        ) {
            gitHubUsersDatabase.getGitHubUserDAO().getGitHubUsers()
        }.flow
    }

    override fun getGithubUserDetails(loginName: String): Flow<Resource<GitHubUserDetails>> {
        return object :
            NetworkBoundResource<GitHubUserDetailsDTO, GitHubUserDetails>() {
            override suspend fun shouldFetch(data: GitHubUserDetails?): Boolean {
                return data == null
            }

            override suspend fun loadFromDb(): GitHubUserDetails {
                return gitHubUsersDatabase.getGitHubUserDetailsDAO().getGitHubUserDetails(loginName)
            }

            override suspend fun createCall(): GitHubUserDetailsDTO {
                return gitHubService.getGitHubUserDetails(loginName)
            }

            override suspend fun saveCallResult(result: GitHubUserDetailsDTO) {
                gitHubUsersDatabase.getGitHubUserDetailsDAO()
                    .insertGitHubUserDetails(result.toDomain())
            }
        }.execute()
    }
}
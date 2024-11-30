package com.steven.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.steven.data.mappers.toDomain
import com.steven.data.network.GitHubService
import com.steven.data.room.GitHubUsersDatabase
import com.steven.data.room.UserKey
import com.steven.domain.models.GitHubUser

@OptIn(ExperimentalPagingApi::class)
class GitHubUserRemoteMediator(
    private val gitHubUsersDatabase: GitHubUsersDatabase,
    private val gitHubService: GitHubService
) : RemoteMediator<Int, GitHubUser>() {

    private val userDao = gitHubUsersDatabase.getGitHubUserDAO()
    private val keyDao = gitHubUsersDatabase.getUserKeyDAO()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GitHubUser>
    ): MediatorResult {
        return try {
            val since = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // Get the last key (user_id) for the next page
                    val lastKey = gitHubUsersDatabase.withTransaction {
                        keyDao.getLastUserKey()
                    }
                    lastKey?.next ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = gitHubService.getGitHubUsers(
                since = since,
                perPage = state.config.pageSize
            )

            val endOfPaginationReached = response.isEmpty()

            gitHubUsersDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.deleteGitHubUsers()
                    keyDao.deleteUserKey()
                }

                val users = response.map { it.toDomain() }
                userDao.insertGitHubUsers(users)

                val keys = users.map { user ->
                    UserKey(
                        id = user.id,
                        prex = null,
                        next = user.id
                    )
                }
                keyDao.insertUserKey(keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}


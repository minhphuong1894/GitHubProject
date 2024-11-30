package com.steven.domain.usecases

import androidx.paging.PagingData
import com.steven.domain.di.IoDispatcher
import com.steven.domain.models.GitHubUser
import com.steven.domain.repositories.GitHubUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGitHubUsersUseCase @Inject constructor(
    private val gitHubUserRepository: GitHubUserRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowPagingUseCase<Unit, GitHubUser>(dispatcher) {

    public override fun execute(
        parameters: Unit
    ): Flow<PagingData<GitHubUser>> {
        return gitHubUserRepository.getGithubUsers()
    }
}
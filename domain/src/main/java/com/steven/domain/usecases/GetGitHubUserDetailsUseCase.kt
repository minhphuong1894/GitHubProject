package com.steven.domain.usecases

import com.steven.common.Resource
import com.steven.domain.di.IoDispatcher
import com.steven.domain.models.GitHubUserDetails
import com.steven.domain.repositories.GitHubUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGitHubUserDetailsUseCase @Inject constructor(
    private val gitHubUserRepository: GitHubUserRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<String, GitHubUserDetails>(dispatcher) {

    public override fun execute(parameters: String): Flow<Resource<GitHubUserDetails>> {
        return gitHubUserRepository.getGithubUserDetails(parameters)
    }
}
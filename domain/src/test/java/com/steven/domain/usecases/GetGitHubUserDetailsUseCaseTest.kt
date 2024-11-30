package com.steven.domain.usecases

import com.steven.common.Resource
import com.steven.domain.models.GitHubUserDetails
import com.steven.domain.repositories.GitHubUserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetGitHubUserDetailsUseCaseTest {

    private lateinit var getGitHubUserDetailsUseCase: GetGitHubUserDetailsUseCase

    private val gitHubUserRepository: GitHubUserRepository = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Initialize mockk
        getGitHubUserDetailsUseCase =
            GetGitHubUserDetailsUseCase(gitHubUserRepository, testDispatcher)
    }

    @Test
    fun testGetGitHubUserDetails_returnUserDetailsSuccess() = runTest(testDispatcher) {
        // Prepare mock data
        val loginName = "testUser"
        val expectedDetails = GitHubUserDetails(
            login = "testUser",
            avatarUrl = "https://example.com/avatar",
            htmlUrl = "https://github.com/testUser",
            location = "Earth",
            followers = 100,
            following = 50
        )

        // Mock the repository method
        coEvery { gitHubUserRepository.getGithubUserDetails(loginName) } returns flowOf(
            Resource.Success(
                expectedDetails
            )
        )

        // Execute the use case
        val resultFlow = getGitHubUserDetailsUseCase.execute(loginName)

        // Now assert the data in the collected items
        resultFlow.collect { result ->
            when (result) {

                is Resource.Success -> {
                    assert(result.data == expectedDetails)
                }

                is Resource.Error -> {
                    assert(false) { "Expected successful response, but got error" }
                }

                else -> {}
            }
        }
    }

    @Test
    fun testGetGitHubUserDetails_returnUserDetailsError() = runTest(testDispatcher) {
        // Prepare mock data
        val loginName = "testUser"
        val expectedErrorMessage = Throwable("User not found")

        // Mock the repository method
        coEvery { gitHubUserRepository.getGithubUserDetails(loginName) } returns flowOf(
            Resource.Error(
                expectedErrorMessage
            )
        )

        // Execute the use case
        val resultFlow = getGitHubUserDetailsUseCase.execute(loginName)

        // Now assert the data in the collected items
        resultFlow.collect { result ->
            when (result) {
                is Resource.Success -> {

                    assert(false) { "Expected error response, but got success" }
                }

                is Resource.Error -> {
                    assert(result.exception == expectedErrorMessage)
                }

                else -> {}
            }
        }
    }
}
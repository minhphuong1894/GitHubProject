package com.steven.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.steven.domain.models.GitHubUser
import com.steven.domain.repositories.GitHubUserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetGitHubUsersUseCaseTest {

    private lateinit var getGitHubUsersUseCase: GetGitHubUsersUseCase

    private val gitHubUserRepository: GitHubUserRepository = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // Initialize mockk
        getGitHubUsersUseCase = GetGitHubUsersUseCase(gitHubUserRepository, testDispatcher)
    }

    @Test
    fun testGetGitHubUsersUseCaseTest_returnSuccess() = runTest {
        // Prepare mock data
        val mockGitHubUser = GitHubUser(
            id = 1,
            login = "user1",
            avatarUrl = "https://avatar.com/user1",
            htmlUrl = "https://github.com/user1"
        )
        val pagingData = PagingData.from(listOf(mockGitHubUser))

        // Mock the repository method to return the PagingData
        coEvery { gitHubUserRepository.getGithubUsers() } returns flowOf(pagingData)

        // Execute the use case
        val resultFlow: Flow<PagingData<GitHubUser>> = getGitHubUsersUseCase.execute(Unit)

        // Collect the result and validate the data
        val collectedItems = resultFlow.toList()

        // Now we can assert the data in the collected items
        assertEquals(1, collectedItems.size)
    }
}
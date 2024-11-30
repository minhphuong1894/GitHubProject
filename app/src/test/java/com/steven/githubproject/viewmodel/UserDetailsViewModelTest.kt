package com.steven.githubproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.steven.common.Constants
import com.steven.common.Resource
import com.steven.domain.models.GitHubUserDetails
import com.steven.domain.repositories.GitHubUserRepository
import com.steven.domain.usecases.GetGitHubUserDetailsUseCase
import com.steven.githubproject.MainTestRule
import com.steven.githubproject.screen.userdetails.UserDetailsState
import com.steven.githubproject.screen.userdetails.UserDetailsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class UserDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainTestRule()

    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle(mapOf(Constants.PARAM_LOGIN_NAME to "testUser1"))
        userDetailsViewModel =
            UserDetailsViewModel(createGetEarnEventDetailUseCase(), savedStateHandle)
    }

    // Helper method to mock the GitHub user details
    private fun mockGitHubUserDetails(): GitHubUserDetails {
        return GitHubUserDetails(
            login = "testUser1",
            avatarUrl = "https://example.com/avatar",
            htmlUrl = "https://github.com/testUser1",
            location = "Earth",
            followers = 100,
            following = 50
        )
    }

    private fun createGetEarnEventDetailUseCase(): GetGitHubUserDetailsUseCase {
        val temp = flowOf(Resource.Success(mockGitHubUserDetails()))
        val repository: GitHubUserRepository = mock {
            on { runBlocking { getGithubUserDetails("testUser1") } } doReturn temp
        }
        return GetGitHubUserDetailsUseCase(repository, coroutineRule.testDispatcher)
    }


    @Test
    fun testGetUserDetails_shouldReturnSuccess() = runTest {

        userDetailsViewModel.getUserDetails("testUser1")
        userDetailsViewModel.userDetails.test {
            val resultNull = awaitItem()
            assertEquals(UserDetailsState(), resultNull)

            val result = awaitItem()
            assertEquals(mockGitHubUserDetails(), result.userDetails)
            cancelAndConsumeRemainingEvents()
        }

    }
}
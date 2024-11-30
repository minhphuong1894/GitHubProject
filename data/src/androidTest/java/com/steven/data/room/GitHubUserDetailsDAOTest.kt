package com.steven.data.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.steven.domain.models.GitHubUserDetails
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GitHubUserDetailsDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: GitHubUsersDatabase
    private lateinit var dao: GitHubUserDetailsDAO

    @Before
    fun setUp() {

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitHubUsersDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.getGitHubUserDetailsDAO()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun clearGitHubUserDetails_shouldReturnValid() = runTest {

        dao.clearGitHubUserDetails()

        val userKey = dao.getGitHubUserDetails("user")

        MatcherAssert.assertThat(userKey, CoreMatchers.equalTo(null))

    }

    @Test
    fun getGitHubUserDetails_shouldReturnSuccess() = runTest {

        dao.clearGitHubUserDetails()

        val mockUserDetails =
            GitHubUserDetails(
                login = "login",
                avatarUrl = "avatar",
                htmlUrl = "html",
                location = "unknow",
                followers = 0,
                following = 0

            )

        dao.insertGitHubUserDetails(mockUserDetails)

        val userKey = dao.getGitHubUserDetails("login")

        MatcherAssert.assertThat(userKey.login, CoreMatchers.equalTo("login"))

    }
}
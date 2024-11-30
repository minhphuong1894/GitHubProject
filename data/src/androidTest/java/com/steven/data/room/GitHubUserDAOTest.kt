package com.steven.data.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.steven.domain.models.GitHubUser
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GitHubUserDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: GitHubUsersDatabase
    private lateinit var dao: GitHubUserDAO

    @Before
    fun setUp() {

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitHubUsersDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.getGitHubUserDAO()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun deleteGitHubUsersTest_shouldReturnValid() = runTest {

        dao.deleteGitHubUsers()

        val userKey = dao.getGitHubUsers()

        MatcherAssert.assertThat(userKey, CoreMatchers.equalTo(null))

    }


    @Test
    fun getGitHubUsersKey_shouldReturnSuccess() = runTest {

        dao.deleteGitHubUsers()

        val mockUsers = listOf(
            GitHubUser(
                id = 1,
                login = "login",
                avatarUrl = "avatar",
                htmlUrl = "html"
            ),
            GitHubUser(
                id = 2,
                login = "login2",
                avatarUrl = "avatar2",
                htmlUrl = "html2"
            )
        )

        dao.insertGitHubUsers(mockUsers)

        // Act: Retrieve the PagingSource and load the data
        val pagingSource = dao.getGitHubUsers()
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Assert: Validate the result
        if (loadResult is PagingSource.LoadResult.Page) {
            MatcherAssert.assertThat(loadResult.data, CoreMatchers.equalTo(mockUsers))
        }
    }
}
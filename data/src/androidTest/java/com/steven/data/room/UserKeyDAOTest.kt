package com.steven.data.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserKeyDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: GitHubUsersDatabase
    private lateinit var dao: UserKeyDAO

    @Before
    fun setUp() {

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitHubUsersDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.getUserKeyDAO()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun deleteUserKeyTest_shouldReturnValid() = runTest {

        dao.deleteUserKey()

        val userKey = dao.getLastUserKey()

        MatcherAssert.assertThat(userKey, CoreMatchers.equalTo(null))

    }


    @Test
    fun getLastUserKey_shouldReturnSuccess() = runTest {

        dao.deleteUserKey()

        dao.insertUserKey(listOf(UserKey(id = 0,prex = 10, next = 20)))

        val userKey = dao.getLastUserKey()

        MatcherAssert.assertThat(userKey, CoreMatchers.equalTo(1))

    }
}


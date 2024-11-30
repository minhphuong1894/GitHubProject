package com.steven.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.steven.domain.models.GitHubUser

@Dao
interface GitHubUserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGitHubUsers(users : List<GitHubUser>)

    @Query("SELECT * FROM GitHubUser")
    fun getGitHubUsers() : PagingSource<Int,GitHubUser>

    @Query("DELETE FROM GitHubUser")
    suspend fun deleteGitHubUsers()
}
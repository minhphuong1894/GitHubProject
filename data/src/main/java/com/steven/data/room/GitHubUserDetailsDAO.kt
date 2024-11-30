package com.steven.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.steven.domain.models.GitHubUserDetails

@Dao
interface GitHubUserDetailsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGitHubUserDetails(users : GitHubUserDetails)

    @Query("SELECT * FROM GitHubUserDetails WHERE login= :loginName")
    fun getGitHubUserDetails(loginName : String) : GitHubUserDetails

    @Query("DELETE FROM GitHubUserDetails")
    suspend fun clearGitHubUserDetails()
}
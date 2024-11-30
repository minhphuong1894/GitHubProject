package com.steven.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.steven.domain.models.GitHubUser
import com.steven.domain.models.GitHubUserDetails

@Database(
    entities = [GitHubUser::class, UserKey::class, GitHubUserDetails::class],
    version = 1,
    exportSchema = false
)
abstract class GitHubUsersDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): GitHubUsersDatabase {
            return Room.databaseBuilder(
                context,
                GitHubUsersDatabase::class.java,
                "GitHubUsersDatabase"
            ).build()
        }
    }

    abstract fun getGitHubUserDAO(): GitHubUserDAO

    abstract fun getUserKeyDAO(): UserKeyDAO

    abstract fun getGitHubUserDetailsDAO(): GitHubUserDetailsDAO
}
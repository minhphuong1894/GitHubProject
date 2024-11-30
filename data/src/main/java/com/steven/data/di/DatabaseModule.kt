package com.steven.data.di

import android.content.Context
import com.steven.data.room.GitHubUserDAO
import com.steven.data.room.GitHubUsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : GitHubUsersDatabase{
        return GitHubUsersDatabase.getInstance(context)
    }

    @Provides
    fun provideDAO(gitHubUsersDatabase: GitHubUsersDatabase) : GitHubUserDAO{
        return gitHubUsersDatabase.getGitHubUserDAO()
    }
}
package com.steven.data.di

import com.steven.data.repositories.GitHubUserRepositoryImpl
import com.steven.domain.repositories.GitHubUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindGitHubUserRepository(
        gitHubUserRepositoryImpl: GitHubUserRepositoryImpl
    ): GitHubUserRepository
}
package com.steven.githubproject.screen.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.steven.domain.models.GitHubUser
import com.steven.domain.usecases.GetGitHubUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val getGitHubUsersUseCase: GetGitHubUsersUseCase
) : ViewModel() {

    private val _users =
        MutableStateFlow<PagingData<GitHubUser>>(PagingData.empty())
    val users: StateFlow<PagingData<GitHubUser>>
        get() = _users

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            getGitHubUsersUseCase.invoke(Unit).cachedIn(viewModelScope)
                .collect {
                    _users.value = it
                }
        }
    }
}
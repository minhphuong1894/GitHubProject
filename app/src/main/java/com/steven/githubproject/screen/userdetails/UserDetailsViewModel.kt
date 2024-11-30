package com.steven.githubproject.screen.userdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steven.common.Constants
import com.steven.common.Resource
import com.steven.domain.usecases.GetGitHubUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getGitHubUserDetailsUseCase: GetGitHubUserDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _userDetails = MutableStateFlow(UserDetailsState())
    val userDetails: StateFlow<UserDetailsState> = _userDetails

    init {
        savedStateHandle.get<String>(Constants.PARAM_LOGIN_NAME)?.let { loginName ->
            getUserDetails(loginName)
        }
    }

    fun getUserDetails(loginName: String) {
        viewModelScope.launch {
            getGitHubUserDetailsUseCase.invoke(loginName).collect {
                when (it) {
                    is Resource.Success -> {
                        _userDetails.value =
                            UserDetailsState(userDetails = it.data, isLoading = false, error = "")
                    }

                    is Resource.Error -> {
                        _userDetails.value = UserDetailsState(error = "Empty User Details")
                    }

                    is Resource.Loading -> {
                        _userDetails.value = UserDetailsState(isLoading = true)
                    }
                }
            }
        }
    }

}
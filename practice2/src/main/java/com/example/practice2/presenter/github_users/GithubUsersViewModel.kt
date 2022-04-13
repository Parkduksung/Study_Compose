package com.example.practice2.presenter.github_users

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice2.common.Resource
import com.example.practice2.domain.use_case.get_users.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GithubUsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _state = mutableStateOf(GithubUsersState())
    val state: State<GithubUsersState> get() = _state

    fun searchUser(q: String) {
        getUsersUseCase(q).onEach { result ->
            when (result) {

                is Resource.Success -> {
                    _state.value = GithubUsersState(users = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value =
                        GithubUsersState(error = result.message ?: "An unexpected error occured")
                }

                is Resource.Loading -> {
                    _state.value = GithubUsersState(isLoading = true)
                }

            }
        }.launchIn(viewModelScope)
    }
}
package com.example.practice2.presenter.github_users

import com.example.practice2.domain.model.GithubUser

data class GithubUsersState(
    val isLoading: Boolean = false,
    val users: List<GithubUser> = emptyList(),
    val error: String = ""
)
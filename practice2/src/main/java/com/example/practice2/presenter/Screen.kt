package com.example.practice2.presenter

sealed class Screen(val route: String) {
    object GithubUsersScreen : Screen("github_users_screen")
    object GithubReposScreen : Screen("github_repos_screen")
}

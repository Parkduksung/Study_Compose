package com.example.practice2.domain.repository

import com.example.practice2.data.remote.dto.GithubUserDto


interface GithubRepository {
    suspend fun getGithubUsers(q: String): GithubUserDto
}
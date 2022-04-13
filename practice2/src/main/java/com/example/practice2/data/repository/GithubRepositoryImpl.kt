package com.example.practice2.data.repository

import com.example.practice2.data.remote.GithubService
import com.example.practice2.data.remote.dto.GithubUserDto
import com.example.practice2.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubService: GithubService
) : GithubRepository {

    override suspend fun getGithubUsers(q: String): GithubUserDto {
        return githubService.getGithubUsers(q)
    }
}
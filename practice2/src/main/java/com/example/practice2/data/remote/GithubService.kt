package com.example.practice2.data.remote

import com.example.practice2.data.remote.dto.GithubUserDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    suspend fun getGithubUsers(
        @Query("q") q: String,
        @Query("type") type: String = "user",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 0,
    ): GithubUserDto

}
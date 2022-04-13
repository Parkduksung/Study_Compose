package com.example.practice2.di

import com.example.practice2.common.Constants
import com.example.practice2.data.remote.GithubService
import com.example.practice2.data.repository.GithubRepositoryImpl
import com.example.practice2.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApi(): GithubService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubRepository(service: GithubService): GithubRepository {
        return GithubRepositoryImpl(service)
    }
}
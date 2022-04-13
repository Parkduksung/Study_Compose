package com.example.practice2.domain.use_case.get_users

import com.example.practice2.common.Resource
import com.example.practice2.domain.model.GithubUser
import com.example.practice2.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val githubRepository: GithubRepository
) {
    operator fun invoke(q: String): Flow<Resource<List<GithubUser>>> = flow {
        try {
            emit(Resource.Loading<List<GithubUser>>())
            val users =
                githubRepository.getGithubUsers(q).items?.map { it.toGithubUser() } ?: emptyList()
            emit(Resource.Success<List<GithubUser>>(users))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<GithubUser>>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<GithubUser>>("Couldn't reach server. Check your internet connection."))
        }
    }
}
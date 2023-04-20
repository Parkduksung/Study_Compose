package com.example.viewpager.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class GetRandomListUseCase @Inject constructor() {
    operator fun invoke(): Flow<List<String>> = flow {
        val list = mutableListOf<String>().apply {
            repeat(10) {
                add(Random.nextInt(1000).toString())
            }
        }
        emit(list)
    }
}
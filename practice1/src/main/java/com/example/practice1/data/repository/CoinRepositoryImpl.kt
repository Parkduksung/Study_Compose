package com.example.practice1.data.repository

import com.example.practice1.data.remote.CoinPaprikaApi
import com.example.practice1.data.remote.dto.CoinDetailDto
import com.example.practice1.data.remote.dto.CoinDto
import com.example.practice1.domain.repository.CoinRepository

class CoinRepositoryImpl(private val coinPaprikaApi: CoinPaprikaApi) : CoinRepository {

    override suspend fun getCoins(): List<CoinDto> {
        return coinPaprikaApi.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return coinPaprikaApi.getCoinById(coinId = coinId)
    }
}
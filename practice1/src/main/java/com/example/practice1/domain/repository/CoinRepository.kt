package com.example.practice1.domain.repository

import com.example.practice1.data.remote.dto.CoinDetailDto
import com.example.practice1.data.remote.dto.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}
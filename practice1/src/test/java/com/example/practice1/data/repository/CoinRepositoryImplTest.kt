package com.example.practice1.data.repository

import com.example.practice1.data.remote.CoinPaprikaApi
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import utils.MockUtil


class CoinRepositoryImplTest {

    private lateinit var coinRepositoryImpl: CoinRepositoryImpl
    private val coinPaprikaApi: CoinPaprikaApi = mock()


    @Before
    fun setUp() {
        coinRepositoryImpl = CoinRepositoryImpl(coinPaprikaApi)
    }

    @Test
    fun getCoinsTest() = runBlocking {

        //given
        whenever(coinPaprikaApi.getCoins()).thenReturn(MockUtil.mockCoinDtoList())

        //when, then
        assertEquals(coinRepositoryImpl.getCoins(), MockUtil.mockCoinDtoList())

    }

    @Test
    fun getCoinByIdTest() = runBlocking {

        //given
        whenever(coinPaprikaApi.getCoinById(coinId = "1")).thenReturn(
            MockUtil.mockCoinDetailDto()
        )

        //when, then
        assertEquals(
            coinRepositoryImpl.getCoinById(coinId = "1"),
            MockUtil.mockCoinDetailDto()
        )

    }
}
package com.example.practice1.domain.use_case.get_coin

import com.example.practice1.common.Resource
import com.example.practice1.data.remote.dto.toCoin
import com.example.practice1.domain.repository.CoinRepository
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Before
import org.junit.Test
import utils.MockUtil


class GetCoinUseCaseTest {

    private lateinit var getCoinUseCase: GetCoinUseCase
    private val coinRepository: CoinRepository = mock()


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        getCoinUseCase = GetCoinUseCase(coinRepository)
    }

    @Test
    fun invokeSuccessTest() = runBlocking {

        //given
        whenever(coinRepository.getCoins()).thenReturn(MockUtil.mockCoinDtoList())

        //when
        getCoinUseCase().take(getCoinUseCase().count()).toList().forEach { resource ->


            when (resource) {

                //then
                is Resource.Loading -> {
                    assertEquals(resource.message, null)
                    assertEquals(resource.data, null)
                }

                is Resource.Success -> {
                    assertEquals(resource.message, null)
                    assertEquals(resource.data, MockUtil.mockCoinDtoList().map { it.toCoin() })
                }
            }
        }
    }


    @Test
    fun invokeFailTest() = runBlocking {

        //given
        coinRepository.stub {
            onBlocking { getCoins() } doAnswer {
                throw IOException("Couldn't reach server. Check your internet connection.")
            }
        }

        //when
        getCoinUseCase().take(getCoinUseCase().count()).toList().forEach { resource ->

            when (resource) {

                //then
                is Resource.Loading -> {
                    assertEquals(resource.message, null)
                    assertEquals(resource.data, null)
                }

                is Resource.Error -> {
                    assertEquals(
                        resource.message,
                        "Couldn't reach server. Check your internet connection."
                    )
                    assertEquals(resource.data, null)
                }
            }
        }
    }

}
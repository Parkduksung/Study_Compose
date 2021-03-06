package com.example.practice1.domain.use_case.get_coins

import com.example.practice1.common.Resource
import com.example.practice1.data.remote.dto.toCoinDetail
import com.example.practice1.domain.repository.CoinRepository
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import utils.MockUtil
import java.io.IOException

class GetCoinsUseCaseTest {

    private lateinit var getCoinUseCase: GetCoinsUseCase
    private val coinRepository: CoinRepository = mock()


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        getCoinUseCase = GetCoinsUseCase(coinRepository)
    }

    @Test
    fun invokeSuccessTest() = runBlocking {

        //given
        whenever(coinRepository.getCoinById(coinId = "1")).thenReturn(MockUtil.mockCoinDetailDto())

        //when
        getCoinUseCase("1").take(getCoinUseCase("1").count()).toList().forEach { resource ->

            when (resource) {

                //then
                is Resource.Loading -> {
                    Assert.assertEquals(resource.message, null)
                    Assert.assertEquals(resource.data, null)
                }

                is Resource.Success -> {
                    Assert.assertEquals(resource.message, null)
                    Assert.assertEquals(
                        resource.data,
                        MockUtil.mockCoinDetailDto().toCoinDetail()
                    )
                }
            }
        }
    }


    @Test
    fun invokeFailTest() = runBlocking {

        //given
        coinRepository.stub {
            onBlocking { getCoinById(coinId = "1") } doAnswer {
                throw IOException("Couldn't reach server. Check your internet connection.")
            }
        }

        //when
        getCoinUseCase(coinId = "1").take(getCoinUseCase(coinId = "1").count()).toList().forEach { resource ->

            when (resource) {

                //then
                is Resource.Loading -> {
                    Assert.assertEquals(resource.message, null)
                    Assert.assertEquals(resource.data, null)
                }

                is Resource.Error -> {
                    Assert.assertEquals(
                        resource.message,
                        "Couldn't reach server. Check your internet connection."
                    )
                    Assert.assertEquals(resource.data, null)
                }
            }
        }
    }

}
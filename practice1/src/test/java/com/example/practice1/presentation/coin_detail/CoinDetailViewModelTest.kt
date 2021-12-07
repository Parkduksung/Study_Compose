package com.example.practice1.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import com.example.practice1.common.Constants
import com.example.practice1.common.Resource
import com.example.practice1.data.remote.dto.toCoinDetail
import com.example.practice1.domain.model.CoinDetail
import com.example.practice1.domain.use_case.get_coins.GetCoinsUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.runBlocking
import org.junit.Test
import utils.MockUtil

class CoinDetailViewModelTest {

    private lateinit var coinDetailViewModel: CoinDetailViewModel
    private val getCoinsUseCase: GetCoinsUseCase = mock()
    private val savedStateHandle: SavedStateHandle = mock()


    @InternalCoroutinesApi
    @Test
    fun getCoinLoadingTest() = runBlocking {

        //given
        whenever(savedStateHandle.get<String>(Constants.PARAM_COIN_ID)).thenReturn("1")
        whenever(
            getCoinsUseCase(
                coinId = savedStateHandle.get<String>(Constants.PARAM_COIN_ID) ?: ""
            )
        ).thenReturn(object : Flow<Resource<CoinDetail>> {
            @InternalCoroutinesApi
            override suspend fun collect(collector: FlowCollector<Resource<CoinDetail>>) {
                collector.emit(Resource.Loading<CoinDetail>())
            }
        })

        //when
        coinDetailViewModel = CoinDetailViewModel(getCoinsUseCase, savedStateHandle)

        //then
        Assert.assertEquals(coinDetailViewModel.state.value.isLoading, true)
        Assert.assertEquals(coinDetailViewModel.state.value.coin, null)
        Assert.assertEquals(coinDetailViewModel.state.value.error, "")
    }

    @InternalCoroutinesApi
    @Test
    fun getCoinSuccessTest() = runBlocking {

        //given
        whenever(savedStateHandle.get<String>(Constants.PARAM_COIN_ID)).thenReturn("1")
        whenever(
            getCoinsUseCase(
                coinId = savedStateHandle.get<String>(Constants.PARAM_COIN_ID) ?: ""
            )
        ).thenReturn(object : Flow<Resource<CoinDetail>> {
            @InternalCoroutinesApi
            override suspend fun collect(collector: FlowCollector<Resource<CoinDetail>>) {
                collector.emit(
                    Resource.Success<CoinDetail>(
                        MockUtil.mockCoinDetailDto().toCoinDetail()
                    )
                )
            }
        })

        //when
        coinDetailViewModel = CoinDetailViewModel(getCoinsUseCase, savedStateHandle)

        //then
        Assert.assertEquals(
            coinDetailViewModel.state.value.coin,
            MockUtil.mockCoinDetailDto().toCoinDetail()
        )
        Assert.assertEquals(coinDetailViewModel.state.value.isLoading, false)
        Assert.assertEquals(coinDetailViewModel.state.value.error, "")
    }

    @InternalCoroutinesApi
    @Test
    fun getCoinFailTest() = runBlocking {

        //given
        whenever(savedStateHandle.get<String>(Constants.PARAM_COIN_ID)).thenReturn("1")
        whenever(
            getCoinsUseCase(
                coinId = savedStateHandle.get<String>(Constants.PARAM_COIN_ID) ?: ""
            )
        ).thenReturn(object : Flow<Resource<CoinDetail>> {
            @InternalCoroutinesApi
            override suspend fun collect(collector: FlowCollector<Resource<CoinDetail>>) {
                collector.emit(Resource.Error<CoinDetail>("Unknown Error"))
            }
        })

        //when
        coinDetailViewModel = CoinDetailViewModel(getCoinsUseCase, savedStateHandle)

        //then
        Assert.assertEquals(coinDetailViewModel.state.value.error, "Unknown Error")
        Assert.assertEquals(coinDetailViewModel.state.value.isLoading, false)
        Assert.assertEquals(coinDetailViewModel.state.value.coin, null)
    }

}
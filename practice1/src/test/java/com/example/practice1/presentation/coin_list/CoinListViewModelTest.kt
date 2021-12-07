package com.example.practice1.presentation.coin_list

import com.example.practice1.common.Resource
import com.example.practice1.data.remote.dto.toCoin
import com.example.practice1.domain.model.Coin
import com.example.practice1.domain.use_case.get_coin.GetCoinUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.runBlocking
import org.junit.Test
import utils.MockUtil

@ExperimentalCoroutinesApi
class CoinListViewModelTest {

    private lateinit var coinListViewModel: CoinListViewModel
    private val getCoinUseCase: GetCoinUseCase = mock()


    @InternalCoroutinesApi
    @Test
    fun getCoinLoadingTest() = runBlocking {

        //given
        whenever(getCoinUseCase()).thenReturn(object : Flow<Resource<List<Coin>>> {
            override suspend fun collect(collector: FlowCollector<Resource<List<Coin>>>) {
                collector.emit(Resource.Loading<List<Coin>>())
            }
        })

        //when
        coinListViewModel = CoinListViewModel(getCoinUseCase)


        //then
        assertEquals(coinListViewModel.state.value.isLoading, true)
        assertEquals(coinListViewModel.state.value.coins, emptyList<Coin>())
        assertEquals(coinListViewModel.state.value.error, "")

    }

    @InternalCoroutinesApi
    @Test
    fun getCoinSuccessTest() = runBlocking {

        //given
        whenever(getCoinUseCase()).thenReturn(object : Flow<Resource<List<Coin>>> {
            override suspend fun collect(collector: FlowCollector<Resource<List<Coin>>>) {
                collector.emit(
                    Resource.Success<List<Coin>>(
                        MockUtil.mockCoinDtoList().map { it.toCoin() })
                )
            }
        })

        //when
        coinListViewModel = CoinListViewModel(getCoinUseCase)


        //then
        assertEquals(
            coinListViewModel.state.value.coins,
            MockUtil.mockCoinDtoList().map { it.toCoin() })
        assertEquals(coinListViewModel.state.value.isLoading, false)
        assertEquals(coinListViewModel.state.value.error, "")

    }

    @InternalCoroutinesApi
    @Test
    fun getCoinFailTest() = runBlocking {


        //given
        whenever(getCoinUseCase()).thenReturn(object : Flow<Resource<List<Coin>>> {
            override suspend fun collect(collector: FlowCollector<Resource<List<Coin>>>) {
                collector.emit(Resource.Error<List<Coin>>(message = "Unknown Error"))
            }
        })

        //when
        coinListViewModel = CoinListViewModel(getCoinUseCase)



        //then
        assertEquals(coinListViewModel.state.value.error, "Unknown Error")
        assertEquals(coinListViewModel.state.value.isLoading, false)
        assertEquals(coinListViewModel.state.value.coins, emptyList<Coin>())
    }
}
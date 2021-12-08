package com.example.practice1.presentation.coin_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.practice1.data.remote.CoinPaprikaApi
import com.example.practice1.data.remote.dto.toCoin
import com.example.practice1.di.TestAppModule
import com.example.practice1.domain.model.Coin
import com.example.practice1.domain.repository.CoinRepository
import com.example.practice1.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import junit.framework.Assert.assertNotSame
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlin.random.Random

@UninstallModules(TestAppModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CoinListScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var paprikaApi: CoinPaprikaApi

    @Inject
    lateinit var coinRepository: CoinRepository

    private lateinit var getCoinUseCase: GetCoinUseCase

    private lateinit var coinListViewModel: CoinListViewModel

    private lateinit var navController: NavController

    @Before
    fun setUp() {
        hiltRule.inject()
        getCoinUseCase = GetCoinUseCase(coinRepository)
        coinListViewModel = CoinListViewModel(getCoinUseCase)

    }

    @Test
    fun progressTest() {

        //given, when
        composeTestRule.setContent {
            navController = rememberNavController()
            CoinListScreen(navController = navController, coinListViewModel)
        }

        //then - before api connection, show progress.
        composeTestRule.onNodeWithTag("progress").assertExists()


        //finish api connection. so state change.
        composeTestRule.waitUntil {
            coinListViewModel.state.value != CoinListState(isLoading = true)
        }

        //then - after api connection, hide progress.
        composeTestRule.onNodeWithTag("progress").assertDoesNotExist()

    }

    @Test
    fun getCoinItemTest() {

        //given
        val getRandomCoin = getRandomCoinItem()

        //when
        composeTestRule.setContent {
            navController = rememberNavController()
            CoinListScreen(navController = navController, coinListViewModel)
        }

        composeTestRule.waitUntil {
            coinListViewModel.state.value != CoinListState(isLoading = true)
        }


        //then
        assertNotSame(getRandomCoin.first, -1)
        assertNotSame(getRandomCoin.second, null)

        composeTestRule.onNodeWithTag(testTag = "lazyCoinListColumn")
            .performScrollToIndex(getRandomCoin.first)

        composeTestRule.onNodeWithText(text = "${getRandomCoin.second?.rank}. ${getRandomCoin.second?.name} (${getRandomCoin.second?.symbol})")
            .assertIsDisplayed()
    }


    private fun getRandomCoinItem(): Pair<Int, Coin?> {
        val list = mutableListOf<Coin>()
        runBlocking {
            list.addAll(coinRepository.getCoins().map { it.toCoin() })
        }
        return if (list.isNotEmpty()) {
            val randomPosition = Random.nextInt(list.lastIndex + 1)
            Pair(randomPosition, list[randomPosition])
        } else {
            Pair(-1, null)
        }
    }
}
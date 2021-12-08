package com.example.practice1.presentation.coin_list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.practice1.data.remote.CoinPaprikaApi
import com.example.practice1.di.AppModule
import com.example.practice1.domain.repository.CoinRepository
import com.example.practice1.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@UninstallModules(AppModule::class)
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
    fun initUi() {
        composeTestRule.setContent {
            navController = rememberNavController()
            CoinListScreen(navController = navController, coinListViewModel)
        }

        composeTestRule.waitUntil {
            coinListViewModel.state.value != CoinListState(isLoading = true)
        }
    }
}
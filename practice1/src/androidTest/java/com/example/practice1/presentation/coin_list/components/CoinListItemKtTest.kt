package com.example.practice1.presentation.coin_list.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.practice1.domain.model.Coin
import org.junit.Rule
import org.junit.Test


class CoinListItemKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun activeTest() {

        //given
        val mockCoin = mockCoin().copy(isActive = true)

        //when
        initUi(mockCoin)

        //then
        composeTestRule.onNodeWithText("active").assertIsDisplayed()
    }

    @Test
    fun inactiveTest() {

        //given
        val mockCoin = mockCoin().copy(isActive = false)

        //when
        initUi(mockCoin)

        //then
        composeTestRule.onNodeWithText("inactive").assertIsDisplayed()
    }

    @Test
    fun contentTest() {

        //given
        val mockCoin = mockCoin().copy("1", true, "BetCoin", 1, "BetCoin is...")

        //when
        initUi(mockCoin)

        //then
        composeTestRule.onNodeWithText("${mockCoin.rank}. ${mockCoin.name} (${mockCoin.symbol})")
            .assertIsDisplayed()
    }


    private fun initUi(mockCoin: Coin) {
        composeTestRule.setContent {
            CoinListItem(coin = mockCoin, onItemClick = {})
        }
    }

    private fun mockCoin() = Coin("", false, "", 1, "")
}
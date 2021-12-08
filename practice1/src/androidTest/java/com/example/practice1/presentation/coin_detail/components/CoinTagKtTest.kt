package com.example.practice1.presentation.coin_detail.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class CoinTagKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun tagTest() {

        //given
        val mockTag = "Mock Tag"

        //when
        composeTestRule.setContent { CoinTag(tag = mockTag) }

        //then
        composeTestRule.onNodeWithText(text = mockTag).assertIsDisplayed()

    }

}
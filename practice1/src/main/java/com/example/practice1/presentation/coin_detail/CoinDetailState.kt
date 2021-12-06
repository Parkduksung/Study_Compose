package com.example.practice1.presentation.coin_detail

import com.example.practice1.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)
package com.example.cryptocurrency.presentation.coins

import com.example.cryptocurrency.domain.model.Coin

sealed class CoinsViewState {

    data class Default(
        val topRankCoins: List<Coin>,
        val coins: List<CoinItem>
    ) : CoinsViewState()

    data class Searching(
        val keyword: String,
        val coins: List<CoinItem>
    ) : CoinsViewState()

    object Loading : CoinsViewState()

    object Empty : CoinsViewState()
}
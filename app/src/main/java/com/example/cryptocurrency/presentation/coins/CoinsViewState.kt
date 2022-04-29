package com.example.cryptocurrency.presentation.coins

sealed class CoinsViewState {

    data class Default(
        val topRankCoins: List<CoinItem>,
        val coins: List<CoinItem>
    ) : CoinsViewState()

    data class Searching(
        val keyword: String,
        val coins: List<CoinItem>
    ) : CoinsViewState()

    object Loading : CoinsViewState()

    object Empty : CoinsViewState()
}
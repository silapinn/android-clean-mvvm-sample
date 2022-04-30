package com.example.cryptocurrency.presentation.coins

import com.example.cryptocurrency.domain.model.Coin

sealed class CoinsViewState {

    data class Default(
        val items: List<CoinItemState>
    ) : CoinsViewState()

    data class Searching(
        val keyword: String,
        val coins: List<CoinItemState>
    ) : CoinsViewState()

    object Loading : CoinsViewState()

    object Empty : CoinsViewState()
}
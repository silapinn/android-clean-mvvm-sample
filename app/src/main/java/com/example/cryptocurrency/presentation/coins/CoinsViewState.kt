package com.example.cryptocurrency.presentation.coins

sealed class CoinsViewState {

    data class Default(
        val listItems: List<CoinListItem>
    ) : CoinsViewState()

    data class Searching(
        val keyword: String,
        val coinLists: List<CoinListItem>
    ) : CoinsViewState()

    object Loading : CoinsViewState()

    object Empty : CoinsViewState()
}
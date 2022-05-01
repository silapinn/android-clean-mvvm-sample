package com.example.cryptocurrency.presentation.coins

sealed class CoinsUiState {

    data class Default(
        val listItems: List<CoinListItem>
    ) : CoinsUiState()

    data class Searching(
        val keyword: String,
        val coinLists: List<CoinListItem>
    ) : CoinsUiState()

    object Loading : CoinsUiState()

    object Empty : CoinsUiState()
}
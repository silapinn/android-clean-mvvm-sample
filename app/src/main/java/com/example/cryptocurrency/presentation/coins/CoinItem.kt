package com.example.cryptocurrency.presentation.coins

import com.example.cryptocurrency.domain.model.Coin

sealed class CoinItem {
    data class Default(val coin: Coin) : CoinItem()

    object FriendInvitation : CoinItem()

    object Loading : CoinItem()
}
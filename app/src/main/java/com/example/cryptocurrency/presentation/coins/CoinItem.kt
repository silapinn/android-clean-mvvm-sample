package com.example.cryptocurrency.presentation.coins

import com.example.cryptocurrency.domain.model.Coin

sealed class CoinItem {

    data class Default(val coin: Coin) : CoinItem()

    object FriendInvite : CoinItem()

    object Loading : CoinItem()
}

enum class CoinItemType {
    COIN,
    FRIEND_INVITE,
    LOADING
}
package com.example.cryptocurrency.presentation.coins

import com.example.cryptocurrency.domain.model.Coin

sealed class CoinListItem(val type: CoinItemType) {

    data class TopRankCrypto(
        val textResId: Int,
        val coins: List<Coin>
    ) : CoinListItem(CoinItemType.TOP_RANK_CRYPTO)

    data class SectionHeadline(val textResId: Int) : CoinListItem(CoinItemType.SECTION_HEADLINE)

    data class CryptoCoin(val coin: Coin) : CoinListItem(CoinItemType.COIN)

    data class FriendInvite(
        val iconResId: Int,
        val descriptionResId: Int,
        val actionResId: Int
    ) : CoinListItem(CoinItemType.FRIEND_INVITE)
}

enum class CoinItemType {
    TOP_RANK_CRYPTO,
    SECTION_HEADLINE,
    COIN,
    FRIEND_INVITE,
}
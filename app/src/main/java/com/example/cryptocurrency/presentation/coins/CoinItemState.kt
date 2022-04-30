package com.example.cryptocurrency.presentation.coins

import com.example.cryptocurrency.domain.model.Coin

sealed class CoinItemState(val type: CoinItemType) {

    data class TopRankCrypto(
        val textResId: Int,
        val coins: List<Coin>
    ) : CoinItemState(CoinItemType.TOP_RANK_CRYPTO)

    data class SectionHeadline(val textResId: Int) : CoinItemState(CoinItemType.SECTION_HEADLINE)

    data class CryptoCoin(val coin: Coin) : CoinItemState(CoinItemType.COIN)

    data class FriendInvite(
        val iconResId: Int,
        val descriptionResId: Int,
        val actionResId: Int
    ) : CoinItemState(CoinItemType.FRIEND_INVITE)
}

enum class CoinItemType {
    TOP_RANK_CRYPTO,
    SECTION_HEADLINE,
    COIN,
    FRIEND_INVITE,
}
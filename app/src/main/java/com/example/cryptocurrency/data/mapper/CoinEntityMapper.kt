package com.example.cryptocurrency.data.mapper

import com.example.cryptocurrency.data.entity.CoinEntity
import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.model.CoinDetail

class CoinEntityMapper {

    fun toCoin(coinEntity: CoinEntity): Coin? {
        val id: String = coinEntity.id ?: return null
        val name: String = coinEntity.name ?: ""
        val symbol: String = coinEntity.symbol ?: ""
        val price: Double = coinEntity.price?.toDouble() ?: 0.0
        val change: Double = coinEntity.change?.toDouble() ?: 0.0
        val iconUrl: String? = coinEntity.iconUrl

        return Coin(
            id = id,
            name = name,
            symbol = symbol,
            price = price,
            change = change,
            iconUrl = iconUrl
        )
    }

    fun toCoinDetail(coinEntity: CoinEntity): CoinDetail? {
        val id: String = coinEntity.id ?: return null
        val name: String = coinEntity.name ?: ""
        val symbol: String = coinEntity.symbol ?: ""
        val price: Double = coinEntity.price?.toDouble() ?: 0.0
        val change: Double = coinEntity.change?.toDouble() ?: 0.0
        val marketCap: Double = coinEntity.marketCap?.toDouble() ?: 0.0
        val color: String? = coinEntity.color
        val iconUrl: String? = coinEntity.iconUrl
        val websiteUrl: String? = coinEntity.websiteUrl

        return CoinDetail(
            id = id,
            name = name,
            symbol = symbol,
            color = color,
            price = price,
            change = change,
            marketCap = marketCap,
            iconUrl = iconUrl,
            websiteUrl = websiteUrl
        )
    }
}
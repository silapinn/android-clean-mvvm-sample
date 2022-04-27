package com.example.cryptocurrency.domain.repository

import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.model.CoinDetail

interface CoinsRepository {
    suspend fun getCoins(
        searchKeyword: String? = null,
        pageOffset: Int? = null,
        pageLimit: Int? = null
    ): List<Coin>

    suspend fun getCoinDetails(id: String): CoinDetail
}
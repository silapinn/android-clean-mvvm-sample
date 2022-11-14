package com.example.cryptocurrency.domain.repository

import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {
    suspend fun getLatestCoins(
        searchKeyword: String? = null,
        pageOffset: Int? = null,
        pageLimit: Int? = null
    ): Flow<List<Coin>>

    suspend fun getCoinDetails(id: String): CoinDetail
}

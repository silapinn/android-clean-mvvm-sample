package com.example.cryptocurrency.data.repository.datasource

import com.example.cryptocurrency.data.entity.CoinEntity
import kotlinx.coroutines.flow.Flow

interface CoinsDataSource {
    suspend fun getLatestCoins(
        searchKeyword: String? = null,
        pageOffset: Int? = null,
        pageLimit: Int? = null,
    ): Flow<List<CoinEntity>>

    suspend fun getCoinDetails(id: String): CoinEntity
}

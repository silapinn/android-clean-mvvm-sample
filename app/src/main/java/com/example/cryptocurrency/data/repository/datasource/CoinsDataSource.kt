package com.example.cryptocurrency.data.repository.datasource

import com.example.cryptocurrency.data.entity.CoinEntity

interface CoinsDataSource {
    suspend fun getCoins(
        searchKeyword: String? = null,
        pageOffset: Int? = null,
        pageLimit: Int? = null,
    ): List<CoinEntity>

    suspend fun getCoinDetails(id: String): CoinEntity
}
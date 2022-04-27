package com.example.cryptocurrency.data.repository.datasource

import com.example.cryptocurrency.data.entity.CoinEntity
import com.example.cryptocurrency.data.network.CoinApi

class CoinsRemoteDataSource(private val coinApi: CoinApi) : CoinsDataSource {

    override suspend fun getCoins(
        searchKeyword: String?,
        pageOffset: Int?,
        pageLimit: Int?
    ): List<CoinEntity> {
        return coinApi.getCoins(
            searchKeyword,
            pageOffset,
            pageLimit
        )
    }

    override suspend fun getCoinDetails(id: String): CoinEntity {
        return coinApi.getCoinDetails(id)
    }
}
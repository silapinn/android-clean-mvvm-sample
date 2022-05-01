package com.example.cryptocurrency.data.repository.datasource

import com.example.cryptocurrency.data.Response
import com.example.cryptocurrency.data.entity.CoinEntity
import com.example.cryptocurrency.data.entity.CryptoEntity
import com.example.cryptocurrency.data.network.CoinApi
import com.example.cryptocurrency.data.repository.CoinsRepositoryImpl
import com.example.cryptocurrency.domain.model.Coin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoinsRemoteDataSource(private val coinApi: CoinApi) : CoinsDataSource {

    companion object {
        private const val REFRESH_INTERVAL: Long = 10000L
    }

    override suspend fun getLatestCoins(
        searchKeyword: String?,
        pageOffset: Int?,
        pageLimit: Int?
    ): Flow<List<CoinEntity>> = flow {
//        do {
            val response: Response<CryptoEntity> = coinApi.getCoins(
                searchKeyword,
                pageOffset,
                pageLimit
            )
            val coins: List<CoinEntity> = response.data.coins
            emit(coins)
            delay(REFRESH_INTERVAL)
//        } while (true)
    }

    override suspend fun getCoinDetails(id: String): CoinEntity {
        return coinApi.getCoinDetails(id)
    }
}
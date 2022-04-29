package com.example.cryptocurrency.data.repository

import com.example.cryptocurrency.data.entity.CoinEntity
import com.example.cryptocurrency.data.mapper.CoinEntityMapper
import com.example.cryptocurrency.data.repository.datasource.CoinsRemoteDataSource
import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.model.CoinDetail
import com.example.cryptocurrency.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinsRepositoryImpl(
    private val coinsRemoteDataSource: CoinsRemoteDataSource,
    private val coinEntityMapper: CoinEntityMapper
) : CoinsRepository {

    override suspend fun getLatestCoins(
        searchKeyword: String?,
        pageOffset: Int?,
        pageLimit: Int?
    ): Flow<List<Coin>> = coinsRemoteDataSource.getLatestCoins(
        searchKeyword,
        pageOffset,
        pageLimit
    ).map { coinEntities: List<CoinEntity> ->
        coinEntities.mapNotNull(coinEntityMapper::toCoin)
    }

    override suspend fun getCoinDetails(id: String): CoinDetail {
        val coinEntity: CoinEntity = coinsRemoteDataSource.getCoinDetails(id)
        val coinDetail = coinEntityMapper.toCoinDetail(coinEntity)
        // TODO: throw exception and handle properly
        return coinDetail!!
    }
}
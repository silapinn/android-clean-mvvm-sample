package com.example.cryptocurrency.data.network

import com.example.cryptocurrency.data.Response
import com.example.cryptocurrency.data.entity.CoinEntity
import com.example.cryptocurrency.data.entity.CryptoEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApi {
    @GET("/v2/coins")
    suspend fun getCoins(
        @Query("search") searchKeyword: String? = null,
        @Query("offset") pageOffset: Int? = null,
        @Query("limit") pageLimit: Int? = null
    ): Response<CryptoEntity>

    @GET("/v2/coin/{uuid}")
    suspend fun getCoinDetails(@Path("uuid") id: String): CoinEntity
}
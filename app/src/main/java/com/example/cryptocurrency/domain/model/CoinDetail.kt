package com.example.cryptocurrency.domain.model

data class CoinDetail(
    val id: String,
    val name: String,
    val symbol: String,
    val price: Double,
    val change: Double,
    val marketCap: Double,
    val color: String?,
    val iconUrl: String?,
    val websiteUrl: String?
)
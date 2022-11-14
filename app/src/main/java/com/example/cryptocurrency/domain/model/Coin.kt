package com.example.cryptocurrency.domain.model

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val price: Double,
    val change: Double,
    val rank: Int,
    val iconUrl: String?,
)

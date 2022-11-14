package com.example.cryptocurrency.data.entity

import com.google.gson.annotations.SerializedName

data class CoinEntity(
    @SerializedName("uuid")
    val id: String?,
    val symbol: String?,
    val name: String?,
    val description: String?,
    val color: String?,
    val iconUrl: String?,
    val websiteUrl: String?,
    val marketCap: String?,
    val price: String?,
    val change: String?,
    val rank: Int?
)

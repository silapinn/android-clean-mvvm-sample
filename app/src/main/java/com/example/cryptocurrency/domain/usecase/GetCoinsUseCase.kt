package com.example.cryptocurrency.domain.usecase

import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.repository.CoinsRepository

interface GetCoinsUseCase {
    fun execute(
        searchKeyword: String? = null,
        pageOffset: Int? = null,
        pageLimit: Int? = null
    ): SingleUseCaseResult<List<Coin>>
}

class GetCoinsUseCaseImpl(private val coinsRepository: CoinsRepository) : GetCoinsUseCase {
    override fun execute(
        searchKeyword: String?,
        pageOffset: Int?,
        pageLimit: Int?
    ): SingleUseCaseResult<List<Coin>> {
        TODO("Not yet implemented")
    }
}
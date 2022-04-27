package com.example.cryptocurrency.domain.usecase

import com.example.cryptocurrency.domain.model.CoinDetail
import com.example.cryptocurrency.domain.repository.CoinsRepository

interface GetCoinDetailsUseCase {
    fun execute(id: String): SingleUseCaseResult<CoinDetail>
}

class GetCoinDetailsUseCaseImpl(private val coinsRepository: CoinsRepository) :
    GetCoinDetailsUseCase {
    override fun execute(id: String): SingleUseCaseResult<CoinDetail> {
        TODO("Not yet implemented")
    }
}
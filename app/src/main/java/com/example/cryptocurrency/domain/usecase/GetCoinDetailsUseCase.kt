package com.example.cryptocurrency.domain.usecase

import com.example.cryptocurrency.domain.model.CoinDetail
import com.example.cryptocurrency.domain.repository.CoinsRepository

interface GetCoinDetailsUseCase {
    suspend fun execute(id: String): SingleUseCaseResult<CoinDetail>
}

class GetCoinDetailsUseCaseImpl(private val coinsRepository: CoinsRepository) :
    GetCoinDetailsUseCase {
    override suspend fun execute(id: String): SingleUseCaseResult<CoinDetail> {
        return try {
            val coinDetail = coinsRepository.getCoinDetails(id)
            SingleUseCaseResult.Success(coinDetail)
        } catch (t: Throwable) {
            SingleUseCaseResult.Failure.GenericError(t)
        }
    }
}
package com.example.cryptocurrency.domain.usecase

import com.example.cryptocurrency.common.error.GenericError
import com.example.cryptocurrency.common.error.ServerError
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
        } catch (e: ServerError) {
            SingleUseCaseResult.Failure(code = e.code, message = e.message, details = e.details)
        } catch (e: GenericError) {
            SingleUseCaseResult.Failure(code = null, message = e.message, details = e.details)
        } catch (t: Throwable) {
            SingleUseCaseResult.Failure(code = null, message = t.message, details = t.toString())
        }
    }
}

package com.example.cryptocurrency.domain.usecase

import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.*

interface GetCoinsUseCase {
    suspend fun execute(
        searchKeyword: String? = null,
        pageOffset: Int? = null,
        pageLimit: Int? = null
    ): Flow<SingleUseCaseResult<List<Coin>>>
}

class GetCoinsUseCaseImpl(private val coinsRepository: CoinsRepository) : GetCoinsUseCase {

    override suspend fun execute(
        searchKeyword: String?,
        pageOffset: Int?,
        pageLimit: Int?
    ): Flow<SingleUseCaseResult<List<Coin>>> = channelFlow {
        try {
            coinsRepository
                .getLatestCoins(searchKeyword, pageOffset, pageLimit)
                .collect { coins ->
                    send(SingleUseCaseResult.Success(coins))
                }
        } catch (t: Throwable) {
            send(SingleUseCaseResult.Failure.GenericError(t))
        }
    }
}
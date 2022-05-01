package com.example.cryptocurrency.domain.usecase

sealed class SingleUseCaseResult<T> {

    data class Success<T>(val data: T) : SingleUseCaseResult<T>()

    data class Failure<T>(
        val code: String?,
        val message: String?,
        val details: String?
    ) : SingleUseCaseResult<T>()
}
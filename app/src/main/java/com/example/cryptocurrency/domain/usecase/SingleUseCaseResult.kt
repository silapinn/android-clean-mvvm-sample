package com.example.cryptocurrency.domain.usecase

sealed class SingleUseCaseResult<T> {

    data class Success<T>(val data: T) : SingleUseCaseResult<T>()

    sealed class Failure<T> : SingleUseCaseResult<T>() {
        data class ServerError<T>(val throwable: Throwable) : Failure<T>()

        data class GenericError<T>(val throwable: Throwable) : Failure<T>()
    }
}
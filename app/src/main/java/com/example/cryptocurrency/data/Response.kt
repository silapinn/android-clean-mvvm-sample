package com.example.cryptocurrency.data

data class Response<T>(
    val status: String?,
    val code: String?,
    val message: String?,
    val data: T
)

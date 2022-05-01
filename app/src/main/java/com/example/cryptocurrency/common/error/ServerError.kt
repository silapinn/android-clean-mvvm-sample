package com.example.cryptocurrency.common.error

class ServerError(
    val code: String? = null,
    override val message: String?,
    val details: String?
) : Error()
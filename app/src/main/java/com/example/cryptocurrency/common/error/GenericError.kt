package com.example.cryptocurrency.common.error

class GenericError(override val message: String?, val details: String?) : Error()
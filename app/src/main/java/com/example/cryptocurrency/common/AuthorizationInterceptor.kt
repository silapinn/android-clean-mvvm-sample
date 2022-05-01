package com.example.cryptocurrency.common

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "x-access-token",
                "coinranking80a2c8426fb612d538c2340c5f82e1fffc573665bea8a85d"
            ).build()
        return chain.proceed(request)
    }
}
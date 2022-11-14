package com.example.cryptocurrency.common

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "x-access-token",
                "coinranking091002287c20d1efb056520c894e8bcd5e0865b39a502f8b"
            ).build()
        return chain.proceed(request)
    }
}

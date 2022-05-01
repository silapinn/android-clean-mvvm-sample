package com.example.cryptocurrency.di

import com.example.cryptocurrency.common.AuthorizationInterceptor
import com.example.cryptocurrency.data.network.CoinApi
import com.example.cryptocurrency.data.repository.datasource.CoinsDataSource
import com.example.cryptocurrency.data.repository.datasource.CoinsRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    factory(named("baseUrl")) { "https://api.coinranking.com" }

    factory {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory {
        AuthorizationInterceptor()
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthorizationInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(20L, TimeUnit.SECONDS)
            .build()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(get<String>(named("baseUrl")))
            .addConverterFactory(get())
            .build()
    }


    single<CoinApi> {
        get<Retrofit>().create(CoinApi::class.java)
    }
}
package com.example.cryptocurrency

import android.app.Application
import com.example.cryptocurrency.di.coinsModule
import com.example.cryptocurrency.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class CryptoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CryptoApplication)
            fragmentFactory()
            modules(coinsModule, networkModule)
        }
    }
}
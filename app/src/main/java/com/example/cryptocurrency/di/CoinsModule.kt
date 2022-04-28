package com.example.cryptocurrency.di

import com.example.cryptocurrency.data.repository.CoinsRepositoryImpl
import com.example.cryptocurrency.domain.repository.CoinsRepository
import com.example.cryptocurrency.domain.usecase.GetCoinDetailsUseCase
import com.example.cryptocurrency.domain.usecase.GetCoinDetailsUseCaseImpl
import com.example.cryptocurrency.domain.usecase.GetCoinsUseCase
import com.example.cryptocurrency.domain.usecase.GetCoinsUseCaseImpl
import com.example.cryptocurrency.presentation.coins.CoinsActivity
import com.example.cryptocurrency.presentation.coins.CoinsFragment
import com.example.cryptocurrency.presentation.coins.CoinsViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coinsModule = module {

    scope<CoinsActivity> {
        fragment {
            CoinsFragment.newInstance()
        }
        viewModel {
            CoinsViewModel(get())
        }

    }

    factory<GetCoinsUseCase> {
        GetCoinsUseCaseImpl(get())
    }

    factory<GetCoinDetailsUseCase> {
        GetCoinDetailsUseCaseImpl(get())
    }

    single<CoinsRepository> {
        CoinsRepositoryImpl(get(), get())
    }
}
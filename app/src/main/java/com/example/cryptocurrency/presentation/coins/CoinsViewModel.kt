package com.example.cryptocurrency.presentation.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.launch

class CoinsViewModel(private val getCoinsUseCase: GetCoinsUseCase) : ViewModel() {

    fun loadCoins() = viewModelScope.launch {
        getCoinsUseCase.execute()

    }
}
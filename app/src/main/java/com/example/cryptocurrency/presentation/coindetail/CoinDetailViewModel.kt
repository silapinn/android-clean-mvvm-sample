package com.example.cryptocurrency.presentation.coindetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.domain.model.CoinDetail
import com.example.cryptocurrency.domain.usecase.GetCoinDetailsUseCase
import com.example.cryptocurrency.domain.usecase.SingleUseCaseResult
import kotlinx.coroutines.launch

class CoinDetailViewModel(
    private val id: String,
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase
) : ViewModel() {

    private val _coinDetail: MutableLiveData<CoinDetail> = MutableLiveData()

    val coinDetail: LiveData<CoinDetail> get() = _coinDetail

    fun loadCoinDetail() = viewModelScope.launch {
        when (val result = getCoinDetailsUseCase.execute(id)) {
            is SingleUseCaseResult.Success -> {
                Log.d("CoinDetailViewModel", result.data.toString())
                _coinDetail.value = result.data
            }
            is SingleUseCaseResult.Failure -> {
                // do nothing
            }
        }
    }
}

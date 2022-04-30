package com.example.cryptocurrency.presentation.coins

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.R
import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.usecase.GetCoinsUseCase
import com.example.cryptocurrency.domain.usecase.SingleUseCaseResult
import kotlinx.coroutines.launch

class CoinsViewModel(private val getCoinsUseCase: GetCoinsUseCase) : ViewModel() {

    companion object {
        private const val pageLimit = 20
    }

    private val _coinsViewState: MutableLiveData<CoinsViewState> = MutableLiveData()

    private val _coins: MutableLiveData<List<Coin>> = MutableLiveData(emptyList())

    val coinsViewState: LiveData<CoinsViewState> get() = _coinsViewState

    private var pageOffset = 0

    fun loadCoins() = viewModelScope.launch {
        Log.d(this::class.java.name, "get coins")
        getCoinsUseCase.execute(pageOffset = pageOffset, pageLimit = pageLimit)
            .collect { result ->
                when (result) {
                    is SingleUseCaseResult.Success -> {
                        val coins = _coins.value?.toMutableList() ?: mutableListOf()
                        val latestCoins = result.data
                        val fromIndex = pageOffset * pageLimit

                        Log.d("CoinsViewModel", "coin size = ${coins.size}")
                        Log.d("CoinsViewModel", "fromIndex = $fromIndex")
                        if (coins.size <= fromIndex) {
                            coins.addAll(latestCoins)
                        } else {
                            latestCoins.forEachIndexed { index, coin ->
                                val replaceIndex = fromIndex + index
                                coins[replaceIndex] = coin
                            }
                        }
                        _coins.value = coins
                        val coinItems = buildCoinItemStates(coins)
                        _coinsViewState.value = CoinsViewState.Default(coinItems)
                    }
                    is SingleUseCaseResult.Failure -> {
                        Log.d("CoinsViewModel", "error")
                    }
                }
            }
    }

    private fun buildCoinItemStates(coins: List<Coin>): List<CoinItemState> {
        val coinItemStates: MutableList<CoinItemState> = mutableListOf()

        val topRankCrypto: CoinItemState.TopRankCrypto =
            buildTopRankCrypto(coins) ?: return emptyList()
        val coinItems: List<CoinItemState> = buildCoinItem(coins)

        coinItemStates.apply {
            add(topRankCrypto)
            add(CoinItemState.SectionHeadline(R.string.crypto_section_headline))
            addAll(coinItems)
        }
        // Add top rank crypto


        // Add crypto section headline and coins


        return coinItemStates
    }

    private fun buildTopRankCrypto(coins: List<Coin>): CoinItemState.TopRankCrypto? {
        val topRankCoins = if (coins.size >= 3) coins.take(3) else coins

        return topRankCoins.takeIf { it.isNotEmpty() }?.let {
            CoinItemState.TopRankCrypto(
                textResId = R.string.top_rank_crypto_section_headline,
                coins = it
            )
        }
    }

    private fun buildCoinItem(coins: List<Coin>): List<CoinItemState> {
        val coinItemStates: MutableList<CoinItemState> = mutableListOf()
        val normalCoins = if (coins.size >= 3) coins.drop(3) else return emptyList()

        // Add coins
        coinItemStates.addAll(normalCoins.map { coin -> CoinItemState.CryptoCoin(coin) })

        // Add friend invite
        val coinCount = normalCoins.size + 1 // number of coins = 20
        var friendInvitePosition = 5 // 5
        while (friendInvitePosition <= coinCount) { // 5 <= 20, 10 <= 20, 20 <= 20
            coinItemStates.add(
                friendInvitePosition - 1,
                CoinItemState.FriendInvite(
                    iconResId = R.drawable.ic_gift,
                    descriptionResId = R.string.item_friend_invite_description,
                    actionResId = R.string.item_friend_invite_action
                )
            ) // size = 21,  size = 22,  size = 23
            friendInvitePosition *= 2
        }

        // 0 coin               0 coin
        // 1 coin               1 coin
        // 2 coin               2 coin
        // 3 coin               3 coin
        // 4 coin               4 friend (size = 21)
        // 5 coin               5 coin
        // 6 coin               6 coin
        // 7 coin               7 coin
        // 8 coin               8 coin
        // 9 coin               9 friend (size = 22)
        // 10 coin              10 coin
        // 11 coin              11 coin
        // 12 coin              12 coin
        // 13 coin              13 coin
        // 14 coin              14 coin
        // 15 coin              15 coin
        // 16 coin              16 coin
        // 17 coin              17 coin
        // 18 coin              18 coin
        // 19 coin              19 friend (size = 23)
        //                      20 coin
        //                      21 coin
        //                      22 coin

        // Add section headline

        return coinItemStates
    }
}
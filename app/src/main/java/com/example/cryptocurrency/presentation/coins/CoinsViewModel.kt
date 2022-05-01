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
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CoinsViewModel(private val getCoinsUseCase: GetCoinsUseCase) : ViewModel() {

    companion object {
        private const val pageLimit = 20
    }

    private val _coinsUiState: MutableLiveData<CoinsUiState> = MutableLiveData(
        CoinsUiState.Default(
            listOf(CoinListItem.SectionHeadline(R.string.crypto_section_headline))
        )
    )

    private val pageToCoins: MutableMap<Int, List<Coin>> = mutableMapOf()

    val coinsUiState: LiveData<CoinsUiState> get() = _coinsUiState

    private var currentPage = 0

    fun loadCoins(page: Int = 0, forceRefresh: Boolean = false) = viewModelScope.launch {
        getCoinsUseCase.execute(pageOffset = if (forceRefresh) 0 else page, pageLimit = pageLimit)
            .onStart {
                showLoading()
                if (forceRefresh) currentPage = 0
                Log.d("CoinsViewModel", "onStart")
            }
            .onEach {
                hideLoading()
                Log.d("CoinsViewModel", "onEach")
                currentPage = page
            }
            .collect { result ->
                when (result) {
                    is SingleUseCaseResult.Success -> {
                        val latestCoins = result.data
                        pageToCoins[page] = latestCoins
                        val coins = pageToCoins.values.flatten()
                        val coinListItems = buildCoinListItems(coins)
                        _coinsUiState.value =
                            CoinsUiState.Default(
                                listItems = coinListItems
                            )
                    }
                    is SingleUseCaseResult.Failure -> {
                        Log.d("CoinsViewModel", "error")
                        showErrorAndRetry()
                    }
                }
            }
    }

    fun retry() {
        hideErrorAndRetry()
        loadCoins(currentPage)
    }

    fun onScrollEnd() {
        val uiState = _coinsUiState.value
        if (uiState is CoinsUiState.Default) {
            val isPaginationLoading =
                uiState.listItems.lastOrNull() is CoinListItem.PaginationLoading
            val isError =
                uiState.listItems.lastOrNull() is CoinListItem.Error
            if (!isPaginationLoading && !isError) {
                loadCoins(currentPage + 1)
            }
        }
    }

    private fun buildCoinListItems(coins: List<Coin>): List<CoinListItem> {
        val coinListItems: MutableList<CoinListItem> = mutableListOf()

        val topRankCrypto: CoinListItem.TopRankCrypto =
            buildTopRankCrypto(coins) ?: return emptyList()
        val coinItems: List<CoinListItem> = buildCoinItems(coins)

        coinListItems.apply {
            add(topRankCrypto)
            add(CoinListItem.SectionHeadline(R.string.crypto_section_headline))
            addAll(coinItems)
        }

        return coinListItems
    }

    private fun buildTopRankCrypto(coins: List<Coin>): CoinListItem.TopRankCrypto? {
        val topRankCoins = if (coins.size >= 3) coins.take(3) else coins

        return topRankCoins.takeIf { it.isNotEmpty() }?.let {
            CoinListItem.TopRankCrypto(
                textResId = R.string.top_rank_crypto_section_headline,
                coins = it
            )
        }
    }

    private fun buildCoinItems(coins: List<Coin>): List<CoinListItem> {
        val coinListItems: MutableList<CoinListItem> = mutableListOf()
        val normalCoins = if (coins.size >= 3) coins.drop(3) else return emptyList()

        // Add coins
        coinListItems.addAll(normalCoins.map { coin -> CoinListItem.CryptoCoin(coin) })

        // Add friend invite
        val coinCount = normalCoins.size + 1 // number of coins = 20
        var friendInvitePosition = 5 // 5
        while (friendInvitePosition <= coinCount) { // 5 <= 20, 10 <= 20, 20 <= 20
            coinListItems.add(
                friendInvitePosition - 1,
                CoinListItem.FriendInvite(
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

        return coinListItems
    }

    private fun showLoading() {
        val uiState = _coinsUiState.value
        if (uiState is CoinsUiState.Default) {
            val listItems = uiState.listItems.toMutableList()
            listItems.add(CoinListItem.PaginationLoading)

            _coinsUiState.value = CoinsUiState.Default(listItems)
        }
    }

    private fun hideLoading() {
        val uiState = _coinsUiState.value
        if (uiState is CoinsUiState.Default) {
            val listItems = uiState.listItems.toMutableList()
            listItems.remove(CoinListItem.PaginationLoading)

            _coinsUiState.value = CoinsUiState.Default(listItems)
        }
    }

    private fun showErrorAndRetry() {
        val uiState = _coinsUiState.value
        if (uiState is CoinsUiState.Default) {
            val listItems = uiState.listItems.toMutableList()
            listItems.add(CoinListItem.Error)

            _coinsUiState.value = CoinsUiState.Default(listItems)
        }
    }

    private fun hideErrorAndRetry() {
        val uiState = _coinsUiState.value
        if (uiState is CoinsUiState.Default) {
            val listItems = uiState.listItems.toMutableList()
            listItems.remove(CoinListItem.Error)

            _coinsUiState.value = CoinsUiState.Default(listItems)
        }
    }
}